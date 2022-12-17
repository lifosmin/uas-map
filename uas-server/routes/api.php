<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\JobController;
use App\Http\Controllers\UserJobController;

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
        Route::get('/', [UserController::class, 'getUser']);
    });

     // Buat job processing'
    Route::get('user/jobs',[JobController::class, 'getJob']);
    Route::get('user/available/jobs',[JobController::class, 'getAvailableJob']);
    Route::get('user/posted/jobs',[JobController::class, 'getJobPosted']);

    Route::post('user/update',[UserController::class, 'userUpdate']);
    Route::post('user/create_job',[JobController::class, 'createJob']);
    Route::post('user/apply_job',[UserController::class, 'applyJob']);
    Route::post('user/accept_job',[UserController::class, 'acceptJob']);
    Route::get('user/applied_job',[JobController::class, 'getAppliedJob']);
    Route::get('user/count_applied_job',[JobController::class, 'getCountAppliedJob']);
    Route::post('user/cancel_job',[JobController::class, 'cancelAppliedJob']);

    Route::get('jobs/applicant/user', [UserJobController::class, 'getApplicantJob']);
    Route::get('jobs/applicant', [JobController::class, 'getJobPostedByUser']);
});

Route::prefix('user')->group(function () {
    Route::post('/signin', [UserController::class, 'signIn']);
    Route::post('/signup',[UserController::class, 'signUp']);
    Route::post('/upload_image',[UserController::class, 'uploadImage']);
});

