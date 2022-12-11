<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class UserController extends Controller
{
    public function signUp()
    {
        try {
            $validate = Validator::make(request()->all(), [
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
