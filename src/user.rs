use std::rc::Rc;
use asset::Asset;
use crate::asset::Asset;

pub struct BucketUser {
    name: String,
    identifier: String,
    assets: Vec<Rc<Asset>>,
}
