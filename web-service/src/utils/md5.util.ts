interface IMd5Util {
  hash: (value: string) => string
}

const Md5Util: IMd5Util = {
  hash: (value) => {
    // TODO: Implement md5 hash
    return value
  }
}

export default Md5Util
