<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Job;


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
            'password' => bcrypt('12345'),
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
    }
}
