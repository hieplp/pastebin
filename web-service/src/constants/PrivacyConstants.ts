import type OptionType from '@/types/option.type'

const PrivacyTypes = {
  Public: 'public',
  Private: 'private'
}

const PrivacyConstants = {
  Types: PrivacyTypes,
  Options: [
    {
      id: PrivacyTypes.Public,
      value: 'public',
      label: 'Public'
    },
    {
      id: PrivacyTypes.Private,
      value: 'private',
      label: 'Private'
    }
  ] as OptionType[]
}

export default PrivacyConstants
