use std::fmt::Error;
use std::fs::File;
use std::io::Read;


fn load_yaml_file(path: &str) -> Result<String, Error> {
    let mut file = File::open(path)?;
    let mut contents = String::new();
    file.read_to_string(&mut contents)?;
    file.close();
    Ok(contents)
}

/// This method searches for a file ending with `.yaml`, .yml` or `.json` and returns the contents of the file as a `String`.
pub(crate) fn load_yaml(path: &str) -> Result<String, Error> {
    // Try to load .yaml
    let yaml = load_yaml_file(path + ".yaml");
    if yaml.is_ok() {
        return yaml;
    }
    // Try to load .yml
    let yaml = load_yaml_file(path + ".yml");
    if yaml.is_ok() {
        return yaml;
    }

    // Try to load .json
    let yaml = load_yaml_file(path + ".json");
    if yaml.is_ok() {
        return yaml;
    }
    Err(format!("Could not find a file ending in .yaml, .yml or .json in {}", path).into())
}