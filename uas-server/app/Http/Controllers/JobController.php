<?php

namespace App\Http\Controllers;
use App\Models\Job;
use App\Models\User;
use App\Models\UserJob;

use Illuminate\Http\Response;

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
                'message' => 'Job created successfully',
                'job' => $job
            ], Response::HTTP_CREATED);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job creation failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getJob(Request $request) {
        $validated = $request->validate([
            "job_id" => "integer|required",
        ]);

        try{
            $job = Job::find($validated['job_id']);
            return response()->json([
                'message' => 'Job get succesfully',
                'job' => $job
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getJobPosted() {
        try {
            $jobs_id = User::find(auth()->user()->id)->applyJob->pluck('job_id')->toArray();
            foreach ($jobs_id as $job){
                $jobs[] = Job::find($job);
                $count[] = UserJob::where('job_id', $job)->count();
            }
            
            return response()->json([
                'message' => 'Job get succesfully',
                'job' => $jobs,
                'count' => $count
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getAvailableJob() {
        try {
            $jobs = Job::where('created_by','!=', auth()->user()->id)->get();

            return response()->json([
                'message' => 'Job get succesfully',
                'job' => $jobs
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
        
    }
}
