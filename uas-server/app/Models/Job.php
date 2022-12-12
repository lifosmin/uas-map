<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Job extends Model
{
    use HasFactory;
    protected $table = 'job';
    public function user()
    {
        return $this->belongsTo(User::class, 'created_by');
    }

    public function applyJob()
    {
        return $this->hasMany(UserJob::class, 'job_id', 'id');
    }

    protected $guarded = [
        'id'
    ];
}
