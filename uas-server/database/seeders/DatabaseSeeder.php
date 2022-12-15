<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Job;
use App\Models\UserJob;



class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // \App\Models\User::factory(10)->create();
        User::create([
            'nama' => 'Admin',
            'email' => 'admin@umn.ac.id',
            'password' => bcrypt('1234578'),
            'tanggal_lahir' => '2002-02-24'
        ]);

        User::create([
            'nama' => 'User',
            'email' => 'user@umn.ac.id',
            'password' => bcrypt('1234578'),
            'tanggal_lahir' => '2002-01-24'
        ]);


        Job::create([
            'job_title' => 'Job test',
            'job_price' => 20000,
            'job_date' => now(),
            'created_by' => 1
        ]);

        Job::create([
            'job_title' => 'Job test 2',
            'job_price' => 30000,
            'job_date' => now(),
            'created_by' => 1
        ]);

        Job::create([
            'job_title' => 'Job test 3',
            'job_price' => 30000,
            'job_date' => now(),
            'created_by' => 2
        ]);

        UserJob::create([
            'job_id' => 1,
            'took_by' => 2
        ]);

        UserJob::create([
            'job_id' => 2,
            'took_by' => 2
        ]);
        UserJob::create([
            'job_id' => 3,
            'took_by' => 1
        ]);
        UserJob::create([
            'job_id' => 1,
            'took_by' => 1
        ]);
    }
}
