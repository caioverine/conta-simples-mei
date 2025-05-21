import React, { useState } from "react";

interface Props {
  onClose: () => void;
  onSave: (data: ReceitaFormData) => void;
}

export interface ReceitaFormData {
  descricao: string;
  valor: number;
  categoria: string;
  data: string;
}

const ModalNovaReceita = ({ onClose, onSave }: Props) => {
  const [form, setForm] = useState<ReceitaFormData>({
    descricao: "",
    valor: 0,
    categoria: "",
    data: "",
  });

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
            name="categoria"
            value={form.categoria}
            onChange={handleChange}
            className="w-full border p-2 rounded"
            required
          >
            <option value="">Selecione uma categoria</option>
            <option value="SERVICO">Serviço</option>
            <option value="PRODUTO">Produto</option>
            <option value="OUTROS">Outros</option>
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
              className="px-4 py-2 rounded bg-blue-600 hover:bg-blue-700"
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