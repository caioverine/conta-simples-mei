import React, { useState, useEffect } from "react";
import { listarCategoriasReceita } from "../../services/categoria-service";
import type { Categoria } from "../../model/categoria-model";
import type { Receita } from "../../model/receita-model";

interface Props {
  onClose: () => void;
  onSave: (data: ReceitaFormData) => void;
  error?: string | null;
  receita?: Receita | null;
}

export interface ReceitaFormData {
  id: string;
  descricao: string;
  valor: number;
  idCategoria: string;
  data: string;
}

const ModalNovaReceita = ({ onClose, onSave, error, receita }: Props) => {
  const [form, setForm] = useState<ReceitaFormData>({
    id: "",
    descricao: "",
    valor: 0,
    idCategoria: "",
    data: "",
  });

  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [loadingCategorias, setLoadingCategorias] = useState(false);

  // Preenche o formulário ao abrir para edição
  useEffect(() => {
    if (receita) {
      setForm({
        id: receita.id,
        descricao: receita.descricao,
        valor: receita.valor,
        idCategoria: receita.categoria.id,
        data: receita.data.slice(0, 10), // yyyy-MM-dd
      });
    } else {
      setForm({
        id: "",
        descricao: "",
        valor: 0,
        idCategoria: "",
        data: "",
      });
    }
  }, [receita]);

  useEffect(() => {
    async function fetchCategorias() {
      setLoadingCategorias(true);
      try {
        const resp = await listarCategoriasReceita();
        setCategorias(resp.data);
      } finally {
        setLoadingCategorias(false);
      }
    }
    fetchCategorias();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: name === "valor" ? parseFloat(value) : value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(form);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg w-full max-w-md p-6">
        <h2 className="text-xl font-bold mb-4 text-[#234557] dark:text-gray-100">
          {receita ? "Editar Receita" : "Nova Receita"}
        </h2>
        {error && (
          <div className="mb-2 rounded bg-red-100 border border-red-400 text-red-700 px-3 py-2 text-sm">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Descrição
            </label>
            <input
              name="descricao"
              type="text"
              placeholder="Descrição"
              value={form.descricao}
              onChange={handleChange}
              className="w-full rounded border border-gray-300 dark:border-gray-700 px-3 py-2 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-[#234557]"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Valor
            </label>
            <input
              name="valor"
              type="number"
              placeholder="Valor"
              value={form.valor}
              onChange={handleChange}
              className="w-full rounded border border-gray-300 dark:border-gray-700 px-3 py-2 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-[#234557]"
              required
              min={0}
              step="0.01"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Categoria
            </label>
            <select
              name="idCategoria"
              value={form.idCategoria}
              onChange={handleChange}
              className="w-full rounded border border-gray-300 dark:border-gray-700 px-3 py-2 text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-[#234557]"
              required
              disabled={loadingCategorias}
            >
              <option value="">
                {loadingCategorias ? "Carregando categorias..." : "Selecione uma categoria"}
              </option>
              {categorias.map((cat) => (
                <option key={cat.id} value={cat.id}>
                  {cat.nome}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Data
            </label>
            <input
              name="data"
              type="date"
              value={form.data}
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

export default ModalNovaReceita;