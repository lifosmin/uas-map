<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Job;
use App\Models\UserJob;

class UserJobController extends Controller
{
    public function applyJob() {
        $userjob = new UserJob();

    }
}
