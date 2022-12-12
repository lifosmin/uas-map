<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class UserJob extends Model
{
    use HasFactory;
    public $timestamps = false;
    protected $table = 'user_job';
    protected $guarded = [
        'id'
    ];
}
