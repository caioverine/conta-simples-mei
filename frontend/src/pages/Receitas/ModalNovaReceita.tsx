import React, { useState, useEffect } from "react";
import { listarCategoriasReceita } from "../../services/categoria-service";
import type { Categoria } from "../../model/categoria-model";

interface Props {
  onClose: () => void;
  onSave: (data: ReceitaFormData) => void;
}

export interface ReceitaFormData {
  descricao: string;
  valor: number;
  idCategoria: string;
  data: string;
}

const ModalNovaReceita = ({ onClose, onSave }: Props) => {
  const [form, setForm] = useState<ReceitaFormData>({
    descricao: "",
    valor: 0,
    idCategoria: "",
    data: "",
  });

  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [loadingCategorias, setLoadingCategorias] = useState(false);

  useEffect(() => {
    async function fetchCategorias() {
      setLoadingCategorias(true);
      try {
        const resp = await listarCategoriasReceita("RECEITA");
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
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md p-6">
        <h2 className="text-xl font-bold mb-4">Nova Receita</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            name="descricao"
            type="text"
            placeholder="Descrição"
            value={form.descricao}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            required
          />
          <input
            name="valor"
            type="number"
            placeholder="Valor"
            value={form.valor}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            required
          />
          <select
            name="idCategoria"
            value={form.idCategoria}
            onChange={handleChange}
            className="w-full border p-2 rounded"
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
          <input
            name="data"
            type="date"
            value={form.data}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            required
          />
          <div className="flex justify-end space-x-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded border text-gray-700"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="px-4 py-2 rounded bg-blue-600 hover:bg-blue-700 text-white"
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