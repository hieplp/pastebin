import type OptionType from '@/types/option.type'

const PrivacyTypes = {
  Public: 'PUBLIC',
  Private: 'PRIVATE'
}

const PrivacyConstants = {
  Types: PrivacyTypes,
  Options: [
    {
      id: PrivacyTypes.Public,
      value: 'PUBLIC',
      label: 'Public'
    },
    {
      id: PrivacyTypes.Private,
      value: 'PRIVATE',
      label: 'Private'
    }
  ] as OptionType[]
}

export default PrivacyConstants
