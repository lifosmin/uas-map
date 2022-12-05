<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Laravel\Sanctum\HasApiTokens;

class UserController extends Controller
{
    public function signUp()
    {
        try {
            $user = new User();
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
