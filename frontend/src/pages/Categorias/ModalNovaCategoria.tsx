import React, { useState } from "react";

interface Props {
  onClose: () => void;
  onSave: (data: CategoriaFormData) => void;
  error?: string | null;
}

export interface CategoriaFormData {
  tipo: string;
  nome: string;
}

const ModalNovaCategoria = ({ onClose, onSave, error }: Props) => {
  const [form, setForm] = useState<CategoriaFormData>({
    tipo: "",
    nome: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(form);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg w-full max-w-md p-6">
        <h2 className="text-xl font-bold mb-4 text-[#234557] dark:text-gray-100">Nova Categoria</h2>
        {error && (
          <div className="mb-2 rounded bg-red-100 border border-red-400 text-red-700 px-3 py-2 text-sm">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Tipo
            </label>
            <select
              name="tipo"
              value={form.tipo}
              onChange={handleChange}
              className="w-full rounded border border-gray-300 dark:border-gray-700 px-3 py-2 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-[#234557]"
              required
            >
              <option value="">Selecione o tipo</option>
              <option value="RECEITA">Receita</option>
              <option value="DESPESA">Despesa</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Nome
            </label>
            <input
              name="nome"
              type="text"
              placeholder="Nome da categoria"
              value={form.nome}
              onChange={handleChange}
              className="w-full rounded border border-gray-300 dark:border-gray-700 px-3 py-2 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-[#234557]"
              required
            />
          </div>
          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              onClick={onClose}
              className="btn-wt transition"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="btn transition"
            >
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ModalNovaCategoria;