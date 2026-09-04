import { clsx, type ClassValue } from 'clsx'
import { twMerge } from 'tailwind-merge'

// ponytail: standard tailwind class merger; handles conflicts and conditionals
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}
