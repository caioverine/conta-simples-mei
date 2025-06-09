import { useState, useEffect } from "react";
import Navbar from "../../components/Navbar";
import { format } from "date-fns";
import { FaPlus, FaEdit, FaTrash, FaChevronLeft, FaChevronRight } from "react-icons/fa";
import { listarReceitas } from "../../services/receita-service";
import type { Receita } from "../../model/receita-model";

const categorias = ["Todos", "Serviços", "Produtos", "Outros"];

const meses = [
  { value: "2025-06", label: "Junho/2025" },
  { value: "2025-05", label: "Maio/2025" },
];

const Receitas = () => {
  const [filtroMes, setFiltroMes] = useState("2025-06");
  const [filtroCategoria, setFiltroCategoria] = useState("Todos");
  const [receitas, setReceitas] = useState<Receita[]>([]);
  const [loading, setLoading] = useState(false);

  // Paginação (exemplo)
  const [page, setPage] = useState(0);
  const [size] = useState(5);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    async function fetchReceitas() {
      setLoading(true);
      try {
        const resp = await listarReceitas(page, size);
        setReceitas(resp.data.content);
        setTotalPages(resp.data.totalPages);
      } finally {
        setLoading(false);
      }
    }
    fetchReceitas();
  }, [page, size]);

  const receitasFiltradas = receitas.filter((r) => {
    const mes = r.data.slice(0, 7);
    const categoriaOk = filtroCategoria === "Todos" || r.categoria === filtroCategoria;
    return mes === filtroMes && categoriaOk;
  });

  return (
    <>
      <Navbar titulo="Receitas" />
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
                      <button className="btn-icn flex items-center gap-2 font-medium">
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
                {loading ? (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400">Carregando...</td>
                  </tr>
                ) : receitasFiltradas.length === 0 ? (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400">Nenhuma receita encontrada.</td>
                  </tr>
                ) : (
                  receitasFiltradas.map((r) => (
                    <tr
                      key={r.id}
                      className="bg-gray-50 hover:bg-gray-100 transition rounded-lg shadow-sm"
                    >
                      <td className="py-2 px-3 rounded-l-lg font-mono text-sm text-center">{format(new Date(r.data), "dd/MM/yyyy")}</td>
                      <td className="py-2 px-3 text-center">{r.categoria}</td>
                      <td className="py-2 px-3 text-center">{r.descricao}</td>
                      <td className="py-2 px-3 font-semibold text-green-600 text-center">R$ {r.valor.toFixed(2)}</td>
                      <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                        <button
                          className="btn-icn-wt transition-colors"
                          title="Editar"
                          aria-label="Editar"
                          style={{ border: "none" }}
                        >
                          <FaEdit size={20} />
                        </button>
                        <button
                          className="btn-icn-wt transition-colors"
                          title="Excluir"
                          aria-label="Excluir"
                          style={{ border: "none" }}
                        >
                          <FaTrash size={20} />
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
            <div className="flex justify-center">
              <nav
                className="inline-flex shadow-sm rounded-full border border-gray-300 bg-white p-1"
                aria-label="Paginação"
              >
                <button
                  onClick={() => setPage(page - 1)}
                  disabled={page === 0}
                  className={`px-2 py-1 text-sm flex items-center justify-center bg-white text-gray-500 hover:bg-gray-100 transition rounded-full
                    ${page === 0 ? "opacity-50 cursor-not-allowed" : ""}
                  `}
                  style={{ border: "none" }}
                  aria-label="Página anterior"
                >
                  <FaChevronLeft />
                </button>
                {[...Array(totalPages)].map((_, idx) => (
                  <button
                    key={idx}
                    onClick={() => setPage(idx)}
                    className={`mx-1 px-2 py-1 text-sm bg-white text-gray-700 hover:bg-gray-100 transition rounded-full
                      ${idx === page ? "!bg-[#234557] text-white" : ""}
                    `}
                    style={{ border: "none" }}
                  >
                    {idx + 1}
                  </button>
                ))}
                <button
                  onClick={() => setPage(page + 1)}
                  disabled={page + 1 >= totalPages}
                  className={`px-2 py-1 text-sm flex items-center justify-center bg-white text-gray-500 hover:bg-gray-100 transition rounded-full
                    ${page + 1 >= totalPages ? "opacity-50 cursor-not-allowed" : ""}
                  `}
                  style={{ border: "none" }}
                  aria-label="Próxima página"
                >
                  <FaChevronRight />
                </button>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Receitas;