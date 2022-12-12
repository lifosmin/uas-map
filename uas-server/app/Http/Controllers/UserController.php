<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;

use function Psy\debug;

class UserController extends Controller
{
    // public function uploadImage(Request $request)
    // {
    //     try {
    //         $validate = Validator::make(request()->all(), [
    //             'image' => 'required|image:jpeg,png,jpg,gif,svg|max:2048',
    //         ]);

    //         if($validate->fails()){
    //             return response()->json([
    //                 'message' => 'Image upload failed',
    //                 'error' => $validate->errors()
    //             ], 400);
    //         }

    //         if($request->hasFile('image')){
    //             $image_uploaded_path = 'public/images/user';
    //             $image = $request->file('image');
    //             $image_name = request('nama') . '_' . time() . '.' . $image->getClientOriginalExtension();
    //             $image->storeAs($image_uploaded_path, $image_name);
    //         }

    //         return response()->json([
    //             'message' => 'Image uploaded successfully',
    //             'image' => $image_name
    //         ], 201);
    //     } catch (\Exception $e) {
    //         return response()->json([
    //             'message' => 'Image upload failed',
    //             'error' => $e->getMessage()
    //         ], 500);
    //     }
    // }

    public function signUp(Request $request)
    {
        try {
            $validate = Validator::make(request()->all(), [
                'image' => 'required|image:jpeg,png,jpg,gif,svg',
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
                $image_uploaded_path = 'public/images/user';
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
            ], 500);
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
                    ], 200);
                } else {
                    return response()->json([
                        'message' => 'User login failed',
                        'error' => 'Invalid password'
                    ], 401);
                }
            } else {
                return response()->json([
                    'message' => 'User login failed',
                    'error' => 'User not found'
                ], 404);
            }
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User login failed',
                'error' => $e->getMessage()
            ], 500);
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
            return response()->json([
                'message' => 'User fetched successfully',
                'user' => $user
            ], 200);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User fetch failed',
                'error' => $e->getMessage()
            ], 500);
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
                    ], 200);
                } else {
                    return response()->json([
                        'message' => 'User login failed',
                        'error' => 'Invalid password'
                    ], 401);
                }
            } else {
                return response()->json([
                    'message' => 'User login failed',
                    'error' => 'User not found'
                ], 404);
            }
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User login failed',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function logout(Request $request)
    {
        try {
            $request->user()->currentAccessToken()->delete();
            return response()->json([
                'message' => 'User logged out successfully'
            ], 200);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User logout failed',
                'error' => $e->getMessage()
            ], 500);
        }
    }
}
