export function ModalSucesso({ mensagem, onClose }: { mensagem: string; onClose: () => void }) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg w-full max-w-sm p-6 flex flex-col items-center">
        <span className="text-green-600 text-3xl mb-2">✔️</span>
        <div className="text-lg font-semibold text-gray-800 dark:text-gray-100 mb-4 text-center">
          {mensagem}
        </div>
        <button
          onClick={onClose}
          className="btn transition"
        >
          OK
        </button>
      </div>
    </div>
  );
}