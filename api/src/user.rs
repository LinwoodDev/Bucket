pub struct BucketUser {
    name: String,
}

impl BucketUser {
    pub fn new(name: String) -> BucketUser {
        BucketUser {
            name,
        }
    }
    pub fn name(&self) -> &str {
        &self.name
    }
}
