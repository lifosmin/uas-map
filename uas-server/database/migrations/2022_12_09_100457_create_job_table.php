<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateJobTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('job', function (Blueprint $table) {
            $table->id();
            $table->string('job_image')->nullable();

            $table->string("job_title");
            $table->string("job_desc")->nullable();
            $table->string("job_location");

            $table->integer("job_price");
            $table->timestamp("job_date")->default(now());
            $table->integer("status")->default(0);
            $table->integer("job_quota")->default(0);
            $table->foreignId('created_by')->constrained('users', 'id');

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('job');
    }
}
