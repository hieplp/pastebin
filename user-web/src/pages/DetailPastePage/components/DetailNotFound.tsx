import { AppLayout } from '@/components/layout'
import { DocumentIcon } from '@/components/icons'
import { navigate } from '@/lib/router'

interface DetailNotFoundProps {
  message?: string
}

export function DetailNotFound({ message }: DetailNotFoundProps) {
  return (
    <AppLayout>
      <div className="flex-1 flex flex-col items-center justify-center py-32 text-center">
        <div className="h-12 w-12 rounded-2xl bg-destructive-500/10 border border-destructive-500/20 flex items-center justify-center text-destructive mb-4">
          <DocumentIcon className="h-6 w-6" />
        </div>
        <h2 className="text-lg font-semibold text-zinc-900 dark:text-zinc-100 mb-1">
          Paste not found
        </h2>
        <p className="text-sm text-zinc-500 mb-6 max-w-sm">
          {message ||
            'This paste may have expired, been deleted, or never existed.'}
        </p>
        <button
          type="button"
          onClick={() => navigate('/')}
          className="btn-primary"
        >
          Create a new paste
        </button>
      </div>
    </AppLayout>
  )
}
