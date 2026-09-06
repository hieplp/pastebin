interface DetailCodeAreaProps {
  content: string
  isRaw?: boolean
}

export function DetailCodeArea({
  content,
  isRaw = false,
}: DetailCodeAreaProps) {
  if (isRaw) {
    return (
      <div className="relative flex-1 flex flex-col p-4 sm:p-5 overflow-auto max-h-[72vh] min-h-105">
        <pre className="font-mono text-[13px] leading-6 text-zinc-900 dark:text-zinc-100 whitespace-pre selection:bg-primary-500/20">
          {content}
        </pre>
      </div>
    )
  }

  /* ---- States ---- */
  const lines = content.split('\n')

  /* ---- Render ---- */
  return (
    <div className="relative flex-1 flex flex-col overflow-auto max-h-[72vh] min-h-105">
      <table className="w-full border-collapse font-mono text-[13px] leading-6 text-left">
        <tbody>
          {lines.map((line, idx) => (
            <tr
              key={idx}
              className="hover:bg-zinc-100/60 dark:hover:bg-zinc-800/30 transition-colors group"
            >
              <td className="w-12 py-0.5 px-3 text-right text-zinc-400 dark:text-zinc-600 group-hover:text-zinc-600 dark:group-hover:text-zinc-400 select-none text-xs align-top border-r border-zinc-200/80 dark:border-zinc-800/80">
                {idx + 1}
              </td>
              <td className="py-0.5 px-4 text-zinc-900 dark:text-zinc-100 whitespace-pre overflow-x-auto align-top selection:bg-primary-500/20">
                {line || '\n'}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
