<?php

namespace App\Http\Controllers;

use App\Models\User;
use App\Models\UserJob;
use App\Models\Job;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;

use function Psy\debug;

class UserController extends Controller
{

    public function signUp(Request $request)
    {
        try {
            $validate = Validator::make(request()->all(), [
                'image' => 'required|image:jpeg,png,jpg,gif,svg|max:2048',
                'nama' => 'required',
                'email' => 'required|email|unique:users',
                'password' => 'required',
                'tanggal_lahir' => 'required',
                'jenis_kelamin' => 'required',
                'alamat' => 'required',
                'no_telp' => 'required',
            ]);
        
            if($validate->fails()){
                return response()->json([
                    'message' => 'User creation failed',
                    'error' => $validate->errors()
                ], 400);
            }

            if($request->hasFile('image')){
                $image_uploaded_path = 'images/user';
                $image = $request->file('image');
                $image_name = request('nama') . '_' . time() . '.' . $image->getClientOriginalExtension();
                $image->storeAs($image_uploaded_path, $image_name);
            }

            $user = new User();
            $user->image = $image_name;
            $user->nama = request('nama');
            $user->email = request('email');
            $user->password = Hash::make(request('password'));
            $user->tanggal_lahir = request('tanggal_lahir');
            $user->jenis_kelamin = request('jenis_kelamin');
            $user->alamat = request('alamat');
            $user->no_telp = request('no_telp');
            $user->save();

            return response()->json([
                'message' => 'User created successfully',
                'user' => $user
            ], 201);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User creation failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function userUpdate(Request $request)
    {
        try {

            if($request->hasFile('image')){
                $image_uploaded_path = 'images/user';
                $image = $request->file('image');
                $image_name = request('nama') . '_' . time() . '.' . $image->getClientOriginalExtension();
                $image->storeAs($image_uploaded_path, $image_name);
            }

            $user = User::where('id', auth()->user()->id);
            $user->image = $image_name;
            $user->nama = request('nama');
            $user->email = request('email');
            $user->password = Hash::make(request('password'));
            $user->tanggal_lahir = request('tanggal_lahir');
            $user->jenis_kelamin = request('jenis_kelamin');
            $user->alamat = request('alamat');
            $user->no_telp = request('no_telp');
            $user->save();

            return response()->json([
                'message' => 'User updated',
                'user' => $user
            ], 201);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User updated',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function refreshToken(){
        try {
            $user = User::where('email', request('email'))->first();
            if ($user) {
                if (Hash::check(request('password'), $user->password)) {
                    $token = $user->createToken('authToken')->plainTextToken;
                    return response()->json([
                        'message' => 'User logged in successfully',
                        'token' => $token
                    ], Response::HTTP_OK);
                } else {
                    return response()->json([
                        'message' => 'User login failed',
                        'error' => 'Invalid password'
                    ], Response::HTTP_UNAUTHORIZED);
                }
            } else {
                return response()->json([
                    'message' => 'User login failed',
                    'error' => 'User not found'
                ], Response::HTTP_NOT_FOUND);
            }
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User login failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getUser(Request $request)
    {
        try {
            $user = $request->user();
            if($user->jenis_kelamin == 1){
                $user->jenis_kelamin = 'Laki-laki';
            } else {
                $user->jenis_kelamin = 'Perempuan';
            }
            $user->image = asset('storage/images/user/' . $user->image);
            return response()->json([
                'message' => 'User fetched successfully',
                'user' => $user
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User fetch failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function signIn()
    {
        try {
            $user = User::where('email', request('email'))->first();
            if ($user) {
                if (Hash::check(request('password'), $user->password)) {
                    $token = $user->createToken('authToken')->plainTextToken;
                    return response()->json([
                        'message' => 'User logged in successfully',
                        'token' => $token
                    ], Response::HTTP_OK);
                } else {
                    return response()->json([
                        'message' => 'User login failed',
                        'error' => 'Invalid password'
                    ], Response::HTTP_UNAUTHORIZED);
                }
            } else {
                return response()->json([
                    'message' => 'User login failed',
                    'error' => 'User not found'
                ], Response::HTTP_NOT_FOUND);
            }
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User login failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function logout(Request $request)
    {
        try {
            $request->user()->currentAccessToken()->delete();
            return response()->json([
                'message' => 'User logged out successfully'
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User logout failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function applyJob(Request $request){
        $validated = $request->validate([
            "job_id" => "integer|required",
        ]);
        $validated['took_by'] = auth()->user()->id;
        $arrayJob = User::find($validated['took_by'])->applyJob->pluck('job_id')->toArray();
        $arrayCreated = User::find($validated['took_by'])->job->pluck('id')->toArray();
        //biar orang ga ngambil job 2 kali
        if(in_array($validated['job_id'], $arrayJob)) {
            return response()->json([
                'message' => 'User applied the job already',
            ], Response::HTTP_NOT_ACCEPTABLE);
        } else if (in_array($validated['job_id'], $arrayCreated)) {
            return response()->json([
                'message' => 'User created the job',
            ], Response::HTTP_NOT_ACCEPTABLE);
        }

        try {
            UserJob::create($validated);

            return response()->json([
                'message' => 'User applied job successfully'
            ], Response::HTTP_CREATED);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User failed applied job',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function acceptJob(Request $request){
        $validated = $request->validate([
            "job_id" => "integer|required",
            "user_id" => "integer|required",
            "action" => "string|in:confirm,reject"
        ]);
        

        $user_job = UserJob::where('job_id', $validated['job_id'])
            ->where('took_by', $validated['user_id'])
            ->first();

        if($validated["action"]=="confirm") {
            $user_job->status = 1;
            $user_job->save();
            return response()->json([
                'message' => 'Job succesfully accepted'
            ], Response::HTTP_CREATED);
        } else if($validated["action"]=="reject") {
            $user_job->status = 2;
            $user_job->save();
            return response()->json([
                'message' => 'Job succesfully rejected'
            ], Response::HTTP_CREATED);
        } 
    }

    public function jobDone(Request $request) {
        $validated = $request->validate([
            "job_id" => "integer|required",
        ]);

        $job = Job::findOrFail($validated['job_id']);
        try {
            $job->status = 1;
            $job->save();
            return response()->json([
                'message' => 'Job succesfully Done'
            ], Response::HTTP_ACCEPTED);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User failed applied job',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
