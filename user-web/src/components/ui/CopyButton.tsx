import { cn } from '@/utils'
import { CheckIcon, CopyIcon } from '@/components/icons'
import { useClipboard } from '@/hooks'
import type { ButtonHTMLAttributes } from 'react'

export interface CopyButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text: string
}

// copy button with copied-state icon and label swapping
export function CopyButton({
  text,
  className = '',
  ...props
}: CopyButtonProps) {
  const { copy, copied } = useClipboard()

  return (
    <button
      type="button"
      title="Copy paste content"
      className={cn('btn-secondary', className)}
      onClick={() => void copy(text)}
      {...props}
    >
      {copied ? (
        <>
          <CheckIcon className="h-3.5 w-3.5 text-primary" />
          <span className="text-primary">Copied!</span>
        </>
      ) : (
        <>
          <CopyIcon className="h-3.5 w-3.5 text-zinc-400" />
          <span>Copy</span>
        </>
      )}
    </button>
  )
}
