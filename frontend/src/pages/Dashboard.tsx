import { useState } from "react";
import { Card } from "react-bootstrap";
import { FaWallet, FaArrowDown, FaArrowUp, FaChartLine, FaPlus } from "react-icons/fa";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import { format } from "date-fns";
import Navbar from "../components/Navbar";

const mockMovimentacoes = [
  { data: "2025-06-04", valor: 1200.5, descricao: "Recebimento de cliente" },
  { data: "2025-06-03", valor: -350.0, descricao: "Pagamento de fornecedor" },
  { data: "2025-06-02", valor: -75.9, descricao: "Compra de material" },
  { data: "2025-06-01", valor: 500.0, descricao: "Transferência recebida" },
];

// Dados para o gráfico (exemplo simples)
const chartData = [
  { dia: "01/06", Receitas: 500, Despesas: 0 },
  { dia: "02/06", Receitas: 0, Despesas: 75.9 },
  { dia: "03/06", Receitas: 0, Despesas: 350 },
  { dia: "04/06", Receitas: 1200.5, Despesas: 0 },
];

export default function Dashboard() {
  const [movimentacoes] = useState(mockMovimentacoes);

  return (
    <>
      <Navbar titulo="Dashboard" />
      <div className="container mx-auto px-4 py-8">
        {/* Cards */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          <Card className="text-center shadow-sm bg-white rounded">
            <Card.Body>
              <FaWallet className="mx-auto mt-2 mb-2 text-2xl text-[#234557]" />
              <Card.Title className="text-sm text-gray-500">Saldo</Card.Title>
              <div className="text-xl font-bold text-[#234557]">R$ 5.000,00</div>
            </Card.Body>
          </Card>
          <Card className="text-center shadow-sm bg-white rounded">
            <Card.Body>
              <FaArrowDown className="mx-auto mt-2 mb-2 text-2xl text-green-600" />
              <Card.Title className="text-sm text-gray-500">Receitas</Card.Title>
              <div className="text-xl font-bold text-green-600">R$ 2.000,00</div>
            </Card.Body>
          </Card>
          <Card className="text-center shadow-sm bg-white rounded">
            <Card.Body>
              <FaArrowUp className="mx-auto mt-2 mb-2 text-2xl text-red-600" />
              <Card.Title className="text-sm text-gray-500">Despesas</Card.Title>
              <div className="text-xl font-bold text-red-600">R$ 800,00</div>
            </Card.Body>
          </Card>
          <Card className="text-center shadow-sm bg-white rounded">
            <Card.Body>
              <FaChartLine className="mx-auto mt-2 mb-2 text-2xl text-[#234557]" />
              <Card.Title className="text-sm text-gray-500">Resultado mensal</Card.Title>
              <div className="text-xl font-bold text-[#234557]">R$ 1.200,00</div>
            </Card.Body>
          </Card>
        </div>

        {/* Gráfico */}
        <div className="bg-white rounded shadow p-6 mb-8">
          <h3 className="text-lg font-semibold text-[#234557] mb-4">Resumo financeiro</h3>
          <ResponsiveContainer width="100%" height={250}>
            <BarChart data={chartData}>
              <XAxis dataKey="dia" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="Receitas" fill="#16a34a" />
              <Bar dataKey="Despesas" fill="#dc2626" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {/* Últimas movimentações */}
        <div className="bg-white rounded shadow p-6">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-[#234557]">Últimas movimentações</h3>
            <div className="flex gap-2">
              <button
                className="p-2 rounded-full text-white flex items-center justify-center"
                title="Adicionar movimentação"
              >
                <FaPlus />
              </button>
            </div>
          </div>
          <table className="w-full text-left">
            <thead>
              <tr>
                <th className="pb-2 text-gray-500 font-medium">Data</th>
                <th className="pb-2 text-gray-500 font-medium">Valor</th>
                <th className="pb-2 text-gray-500 font-medium">Descrição</th>
              </tr>
            </thead>
            <tbody>
              {movimentacoes.map((mov, idx) => (
                <tr key={idx} className="border-t">
                  <td className="py-2">{format(new Date(mov.data), "dd/MM/yyyy")}</td>
                  <td className={`py-2 font-semibold ${mov.valor < 0 ? "text-red-600" : "text-green-600"}`}>
                    {mov.valor < 0 ? "-" : ""}R$ {Math.abs(mov.valor).toFixed(2)}
                  </td>
                  <td className="py-2">{mov.descricao}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}