import { useState } from "react";
import Navbar from "../../components/Navbar";
import { format } from "date-fns";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";

// Mock de despesas
const mockDespesas = [
  { data: "2025-06-02", categoria: "Fornecedores", descricao: "Compra de insumos", valor: 500 },
  { data: "2025-06-12", categoria: "Operacional", descricao: "Conta de luz", valor: 200 },
  { data: "2025-05-18", categoria: "Materiais", descricao: "Material de escritório", valor: 150 },
  { data: "2025-05-25", categoria: "Outros", descricao: "Taxa bancária", valor: 50 },
];

const categorias = ["Todos", "Fornecedores", "Operacional", "Materiais", "Outros"];

const meses = [
  { value: "2025-06", label: "Junho/2025" },
  { value: "2025-05", label: "Maio/2025" },
];

const Despesas = () => {
  const [filtroMes, setFiltroMes] = useState("2025-06");
  const [filtroCategoria, setFiltroCategoria] = useState("Todos");

  const despesasFiltradas = mockDespesas.filter((d) => {
    const mes = d.data.slice(0, 7);
    const categoriaOk = filtroCategoria === "Todos" || d.categoria === filtroCategoria;
    return mes === filtroMes && categoriaOk;
  });

  return (
    <>
      <Navbar titulo="Despesas" />
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          <div className="overflow-x-auto bg-white rounded-lg pt-2 pb-2 shadow-sm">
            <table className="min-w-full border-separate border-spacing-y-2">
              <thead>
                {/* Linha de filtros + botão */}
                <tr>
                  <th colSpan={5} className="pb-2 px-3 bg-gray-50 rounded-t-lg">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-2">
                      <div className="flex gap-2 flex-wrap">
                        <select
                          className="rounded border-gray-300 px-3 py-2 text-sm"
                          value={filtroMes}
                          onChange={e => setFiltroMes(e.target.value)}
                        >
                          {meses.map(m => (
                            <option key={m.value} value={m.value}>{m.label}</option>
                          ))}
                        </select>
                        <select
                          className="rounded border-gray-300 px-3 py-2 text-sm"
                          value={filtroCategoria}
                          onChange={e => setFiltroCategoria(e.target.value)}
                        >
                          {categorias.map(c => (
                            <option key={c} value={c}>{c}</option>
                          ))}
                        </select>
                      </div>
                      <button className="btn-icn flex items-center gap-2">
                        <FaPlus />
                      </button>
                    </div>
                  </th>
                </tr>
                {/* Cabeçalho da tabela */}
                <tr>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-l-lg text-center">Data</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Categoria</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Descrição</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Valor</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-r-lg text-center">Ações</th>
                </tr>
              </thead>
              <tbody>
                {despesasFiltradas.length === 0 && (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400">Nenhuma despesa encontrada.</td>
                  </tr>
                )}
                {despesasFiltradas.map((d, idx) => (
                  <tr
                    key={idx}
                    className="bg-gray-50 hover:bg-gray-100 transition rounded-lg shadow-sm"
                  >
                    <td className="py-2 px-3 rounded-l-lg font-mono text-sm text-center">{format(new Date(d.data), "dd/MM/yyyy")}</td>
                    <td className="py-2 px-3 text-center">{d.categoria}</td>
                    <td className="py-2 px-3 text-center">{d.descricao}</td>
                    <td className="py-2 px-3 font-semibold text-red-600 text-center">R$ {d.valor.toFixed(2)}</td>
                    <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                      <button
                        className="btn-icn-wt transition-colors"
                        title="Editar"
                        aria-label="Editar"
                        type="button"
                        style={{ background: "transparent", border: "none" }}
                      >
                        <FaEdit size={20} />
                      </button>
                      <button
                        className="btn-icn-wt transition-colors"
                        title="Excluir"
                        aria-label="Excluir"
                        type="button"
                        style={{ background: "transparent", border: "none" }}
                      >
                        <FaTrash size={20} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default Despesas;