<?php

use App\Http\Controllers\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->group(function () {

    Route::prefix('token')->group(function () {
        Route::post('/logout', [UserController::class, 'logout']);
        Route::post('/refresh', [UserController::class, 'refreshToken']);
    });
    
    Route::prefix('user')->group(function () {
        Route::get('/', [UserController::class, 'user']);
        Route::post('/signin', [UserController::class, 'signIn']);
        Route::post('/signup',[UserController::class, 'signUp']);
    });
});