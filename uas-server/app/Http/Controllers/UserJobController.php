<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Job;
use App\Models\User;

use Illuminate\Http\Response;

class UserJobController extends Controller
{
    public function getApplicantJob(Request $request){
        try {
            $user_id = Job::find($request["job_id"])->applyJob->pluck('took_by')->toArray();
            foreach ($user_id as $user){

                $user_array[] = User::find($user);
                
            }
            foreach($user_array as $user) {
                $user['image'] = asset('storage/images/user/'.$user['image']);
                
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
