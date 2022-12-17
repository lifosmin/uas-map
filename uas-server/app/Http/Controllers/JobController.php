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
                $jobfind = Job::find($job);
                $jobfind->count = UserJob::where('job_id', $job)->count();
                $jobs[] = $jobfind;
            }
            
            return response()->json([
                'message' => 'Job get succesfully',
                'job' => $jobs,
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
            $jobs_id = User::find(auth()->user()->id)->applyJob->pluck('job_id')->toArray();
            $jobs = Job::where('created_by', '!=', auth()->user()->id)->whereNotIn('id', $jobs_id)->get();
            foreach ($jobs as $job){
                $job->job_image = asset('storage/images/job/' . $job->job_image);
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

    public function getCountAppliedJob(){
        try {
            $jobs_id = User::find(auth()->user()->id)->applyJob->pluck('job_id')->toArray();
            $count = Job::whereIn('id', $jobs_id)->count();
            return response()->json([
                'message' => 'Job get succesfully',
                'count' => $count
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function cancelAppliedJob(){
        $validated = request()->validate([
            "job_id" => "integer|required",
        ]);

        try {
            // get $user_Job by using authorization sanctum token and job_id from request
            $user_job = UserJob::where('took_by', auth()->user()->id)->where('job_id', $validated['job_id'])->first();

            // if $userjob status is 0, user can still delete
            if($user_job->status == 0){
                $user_job->delete();
                return response()->json([
                    'message' => 'Job cancelled succesfully',
                ], Response::HTTP_OK);
            } else {
                return response()->json([
                    'message' => 'Job cancelled failed',
                    'error' => 'Job already approved'
                ], Response::HTTP_INTERNAL_SERVER_ERROR);
            }

            return response()->json([
                'message' => 'Job cancelled succesfully',
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job cancelled failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getJobPostedByUser() {
        try{
            $jobs = Job::where('created_by', auth()->user()->id)->get();
            foreach ($jobs as $job){
                $job->job_image = asset('storage/images/job/' . $job->job_image);
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

    public function getAppliedJob() {
        try {
            $jobs = User::find(auth()->user()->id)->applyJob->pluck('job_id')->toArray();
            $userjobs = User::find(auth()->user()->id)->applyJob->pluck('id')->toArray();
            $i = 0;
            foreach ($jobs as $job){
                $jobfind = Job::find($job);
                $jobfind->review_status = UserJob::where('id', $userjobs[$i])->get('status')->first()->status;
                $job_array[] = $jobfind;
                $i++;
            }
            foreach($job_array as $job) {
                $job['job_image'] = asset('storage/images/job/'.$job['job_image']);
                
            }

            return response()->json([
                'message' => 'Job get succesfully',
                'job' => $job_array,

            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Job get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function getAppliedUser(Request $request) {

        try {
            $user_id = Job::find($request["job_id"])->applyJob->pluck('took_by')->toArray();
            foreach ($user_id as $user){

                $user_array[] = User::find($user);
                
            }
            foreach($user_array as $user) {
                $user['image'] = asset('storage/images/job/'.$user['image']);
                
            }

            return response()->json([
                'message' => 'User get succesfully',
                'users' => $user_array
            ], Response::HTTP_OK);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'User get failed',
                'error' => $e->getMessage()
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
