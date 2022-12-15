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
        $validated = request()->validate([
            "judul" => "string|required",
            "deskripsi" => "string|required",
            "gaji" => "integer|required",
            "lokasi" => "string|required",
            "image" => "required|image:jpeg,png,jpg,gif,svg|max:2048",
        ]);

        try {
            if(request()->hasFile('image')){
                $image_uploaded_path = 'images/job';
                $image = request()->file('image');
                $image_name = request('judul') . '_' . time() . '.' . $image->getClientOriginalExtension();
                $image->storeAs($image_uploaded_path, $image_name);
            }

            $job = new Job();
            $job->job_title = $validated['judul'];
            $job->job_desc = $validated['deskripsi'];
            $job->job_price = $validated['gaji'];
            $job->job_location = $validated['lokasi'];
            $job->job_image = $image_name;
            $job->created_by = auth()->user()->id;
            $job->save();

            return response()->json([
                'message' => 'Job created successfully',
                'job' => $job
            ], Response::HTTP_OK);
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
            $jobs = Job::where('created_by', '!=', auth()->user()->id)->get();
            foreach ($jobs as $job){
                $job->job_image = asset('images/job/' . $job->job_image);
            }

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
