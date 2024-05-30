import daisyui from 'daisyui'

/** @type {import('tailwindcss').Config} */
export default {
  purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  content: [],
  theme: {
    extend: {}
  },
  plugins: [
    daisyui
  ]
}

