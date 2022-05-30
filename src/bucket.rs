use std::rc::Rc;
use crate::asset::Asset;
use crate::user::BucketUser;

pub struct Bucket {
    name: String,
    description: String,
    assets: Vec<Asset>,
    users: Vec<BucketUser>,
}


