import React, { useState } from "react";
import type { ReceitaFormData } from "../components/ModalNovaReceita";
import ModalNovaReceita from "../components/ModalNovaReceita";

const Receitas = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [receitas, setReceitas] = useState<ReceitaFormData[]>([]);

    const handleNovaReceita = (novaReceita: ReceitaFormData) => {
        setReceitas((prev) => [...prev, novaReceita]);
        // Aqui futuramente será feita uma chamada POST para o backend
    };
    return (
        <div className="p-6">
        <div className="flex justify-between items-center mb-4">
            <h1 className="text-2xl font-bold">Receitas</h1>
            <button
            onClick={() => setIsModalOpen(true)}
            className="bg-blue-600 px-4 py-2 rounded hover:bg-blue-700"
            >
            Nova Receita
            </button>
        </div>

        {isModalOpen && (
            <ModalNovaReceita
            onClose={() => setIsModalOpen(false)}
            onSave={handleNovaReceita}
            />
        )}

        {/* Lista de receitas */}
        <table className="min-w-full bg-white rounded shadow">
            <thead className="bg-gray-100">
            <tr>
                <th className="px-4 py-2">Descrição</th>
                <th className="px-4 py-2">Valor</th>
                <th className="px-4 py-2">Categoria</th>
                <th className="px-4 py-2">Data</th>
            </tr>
            </thead>
            <tbody>
            {receitas.map((r, index) => (
                <tr key={index} className="border-t">
                <td className="px-4 py-2">{r.descricao}</td>
                <td className="px-4 py-2">R$ {r.valor.toFixed(2)}</td>
                <td className="px-4 py-2">{r.categoria}</td>
                <td className="px-4 py-2">{r.data}</td>
                </tr>
            ))}
            </tbody>
        </table>
        </div>
    );
};

export default Receitas;