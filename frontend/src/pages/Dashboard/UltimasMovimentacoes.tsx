import { FaPlus } from "react-icons/fa";
import { format, parseISO } from "date-fns";

interface Movimentacao {
  data: string;
  valor: number;
  descricao: string;
  categoria?: string;
}

interface UltimasMovimentacoesProps {
  movimentacoes: Movimentacao[];
}

export default function UltimasMovimentacoes({ movimentacoes }: UltimasMovimentacoesProps) {
  return (
    <div className="bg-white rounded shadow p-6">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-[#234557]">Últimas movimentações</h3>
        <div className="flex gap-2">
          <button
            className="btn-icn flex items-center justify-center"
            title="Adicionar movimentação"
          >
            <FaPlus />
          </button>
        </div>
      </div>
      <div className="overflow-x-auto">
        <table className="min-w-full text-left border-separate border-spacing-y-2">
          <thead>
            <tr>
              <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-l-lg text-center">Data</th>
              <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Valor</th>
              <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Descrição</th>
              <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-r-lg text-center">Categoria</th>
            </tr>
          </thead>
          <tbody>
            {movimentacoes.map((mov, idx) => (
              <tr
                key={idx}
                className="bg-gray-50 hover:bg-gray-100 transition rounded-lg shadow-sm"
              >
                <td className="py-2 px-3 rounded-l-lg font-mono text-sm text-center">{format(parseISO(mov.data), "dd/MM/yyyy")}</td>
                <td
                  className={`py-2 px-3 font-semibold text-center ${
                    mov.valor < 0
                      ? "text-red-600"
                      : "text-green-600"
                  }`}
                >
                  {mov.valor < 0 ? "-" : ""}
                  R$ {Math.abs(mov.valor).toFixed(2)}
                </td>
                <td className="py-2 px-3 text-center">{mov.descricao}</td>
                <td className="py-2 px-3 rounded-r-lg text-center">{mov.categoria || "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}