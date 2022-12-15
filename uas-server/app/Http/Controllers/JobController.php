<?php

namespace App\Http\Controllers;
use App\Models\Job;

use Illuminate\Http\Request;

class JobController extends Controller
{
    // Funtion to create job

    public function createJob() {
        try {
            $job = new Job();
            $job->job_title = request('job_title');
            $job->job_desc = request('job_desc');
            $job->job_price = request('job_price');
            $job->created_by = auth()->user()->id;
            $job->save();


            return response()->json([
                'message' => 'User created successfully',
                'job' => $job
            ], 201);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User creation failed',
                'error' => $e->getMessage()
            ], 500);
        }
    }
    public function getApplyJob() {
        try {
            $jobs = Job::get()->toArray();


            return response()->json([
                'message' => 'User created successfully',
                'job' => $jobs
            ], 201);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User creation failed',
                'error' => $e->getMessage()
            ], 500);
        }
    }
}
