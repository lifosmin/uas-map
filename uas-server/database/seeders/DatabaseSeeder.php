<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Job;
use App\Models\UserJob;
use Illuminate\Support\Facades\Hash;




class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // // \App\Models\User::factory(10)->create();
        // User::create([
        //     'image' => 'Admin_1671083161.jpg',
        //     'nama' => 'Admin',
        //     'email' => 'admin@umn.ac.id',
        //     'password' => Hash::make('1234578'),
        //     'tanggal_lahir' => '10 Oktober 2002'
        // ]);

        // User::create([
        //     'image' => 'Suzy_1670874577.jpg',
        //     'nama' => 'User',
        //     'email' => 'user@umn.ac.id',
        //     'password' => Hash::make('1234578'),
        //     'tanggal_lahir' => '15 Oktober 2002'
        // ]);


        // Job::create([
        //     'job_title' => 'Job test',
        //     'job_price' => 20000,
        //     'job_date' => now(),
        //     'job_location' => "ada dehh",
        //     'job_quota' => 1,

        //     'created_by' => 1
        // ]);

        // Job::create([
        //     'job_title' => 'Job test 2',
        //     'job_price' => 30000,
        //     'job_date' => now(),
        //     'job_location' => "ada dehh",
        //     'job_quota' => 1,

        //     'created_by' => 1
        // ]);

        // Job::create([
        //     'job_title' => 'Job test 3',
        //     'job_price' => 30000,
        //     'job_date' => now(),
        //     'job_location' => "ada dehh",
        //     'job_quota' => 1,

        //     'created_by' => 2
        // ]);

        // UserJob::create([
        //     'job_id' => 1,
        //     'took_by' => 2
        // ]);

        // UserJob::create([
        //     'job_id' => 2,
        //     'took_by' => 2
        // ]);
        // UserJob::create([
        //     'job_id' => 3,
        //     'took_by' => 1
        // ]);
        // UserJob::create([
        //     'job_id' => 1,
        //     'took_by' => 1
        // ]);
    }
}
