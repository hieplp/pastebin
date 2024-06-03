import type OptionType from '@/types/option.type'
import { oneDark } from '@codemirror/theme-one-dark'
import { javascript } from '@codemirror/lang-javascript'
import { dracula } from '@uiw/codemirror-theme-dracula'
import { html } from '@codemirror/lang-html'

const CodeMirrorOptions = {
  Type: {
    Language: 'Language',
    Theme: 'Theme',
    TabSize: 'TabSize'
  },
  Languages: [
    {
      id: 'html',
      label: 'HTML',
      value: html()
    },
    {
      id: 'javascript',
      label: 'JavaScript',
      value: javascript()
    }
  ] as OptionType[],
  TabSizes: [
    {
      id: '2',
      label: '2',
      value: 2
    },
    {
      id: '4',
      label: '4',
      value: 4
    }
  ] as OptionType[],
  Themes: [
    {
      id: 'oneDark',
      label: 'One Dark',
      value: oneDark
    },
    {
      id: 'dracula',
      label: 'Dracula',
      value: dracula
    }
  ] as OptionType[]
}

export default CodeMirrorOptions
