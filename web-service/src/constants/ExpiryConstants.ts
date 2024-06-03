import type OptionType from '@/types/option.type'

const ExpiryTypes = {
  Never: 'never',
  Day: '1',
  Week: '7',
  Month: '30',
  ThreeMonths: '90',
  SixMonths: '180',
  Year: '365'
}

const ExpiryConstants = {
  Types: ExpiryTypes,
  Options: [
    {
      id: ExpiryTypes.Never,
      value: 'never',
      label: 'Never'
    },
    {
      id: ExpiryTypes.Day,
      value: '1',
      label: '1 day'
    },
    {
      id: ExpiryTypes.Week,
      value: '7',
      label: '1 week'
    },
    {
      id: ExpiryTypes.Month,
      value: '30',
      label: '1 month'
    },
    {
      id: ExpiryTypes.ThreeMonths,
      value: '90',
      label: '3 months'
    },
    {
      id: ExpiryTypes.SixMonths,
      value: '180',
      label: '6 months'
    },
    {
      id: ExpiryTypes.Year,
      value: '365',
      label: '1 year'
    }
  ] as OptionType[]
}

export default ExpiryConstants
