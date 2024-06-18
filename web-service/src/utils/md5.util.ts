import MD5 from 'crypto-js/md5'

interface IMd5Util {
  hash: (value: string) => string
}

const Md5Util: IMd5Util = {
  hash: (value) => {
    return MD5(value).toString()
  }
}

export default Md5Util
