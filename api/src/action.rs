pub enum BucketAction {
    AddAsset {
        asset : String,
        description : String,
    },
    PostUpdate {
        asset : String,
        description : String,
        name: String,
        track: String,
    },
    RemoveAsset {
        asset : String,
    },
    RemoveUser {
        user : String,
    },
    ChangeUser {
        user: String,
        name: String,
        website: String
    }
}

pub struct BucketRequest {
    action: BucketAction,
    user: String,
}

impl BucketRequest {
    pub fn new(action: BucketAction, user: String) -> BucketRequest {
        BucketRequest {
            action,
            user,
        }
    }
}
