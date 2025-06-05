import { useState } from "react";
import { format, parseISO } from "date-fns";
import Navbar from "../../components/Navbar";
import DashboardCards from "./DashboardCards";
import SaldoAcumuladoChart from "./SaldoAcumuladoChart";
import DespesasPorCategoriaPie from "./DespesasPorCategoriaPie";
import UltimasMovimentacoes from "./UltimasMovimentacoes";

// Mock de movimentações com categoria
const mockMovimentacoes = [
  { data: "2025-01-10", valor: 1200.5, descricao: "Recebimento de cliente", categoria: "Receita" },
  { data: "2025-01-15", valor: -350.0, descricao: "Pagamento de fornecedor", categoria: "Fornecedores" },
  { data: "2025-02-05", valor: -75.9, descricao: "Compra de material", categoria: "Materiais" },
  { data: "2025-02-20", valor: 500.0, descricao: "Transferência recebida", categoria: "Receita" },
  { data: "2025-03-01", valor: 800.0, descricao: "Receita extra", categoria: "Receita" },
  { data: "2025-03-10", valor: -200.0, descricao: "Despesa operacional", categoria: "Operacional" },
];

// Gera o saldo acumulado mês a mês, limitado aos últimos 6 meses
function getSaldoAcumuladoPorMesUltimos6Meses(movimentacoes: typeof mockMovimentacoes) {
  const balancoPorMes: { [mes: string]: number } = {};

  movimentacoes.forEach((mov) => {
    const mes = format(parseISO(mov.data), "MM/yyyy");
    balancoPorMes[mes] = (balancoPorMes[mes] || 0) + mov.valor;
  });

  // Ordena os meses
  const mesesOrdenados = Object.keys(balancoPorMes).sort((a, b) => {
    const [ma, ya] = a.split("/").map(Number);
    const [mb, yb] = b.split("/").map(Number);
    return ya !== yb ? ya - yb : ma - mb;
  });

  // Calcula saldo acumulado mês a mês
  let acumulado = 0;
  const saldoAcumuladoPorMes = mesesOrdenados.map((mes) => {
    acumulado += balancoPorMes[mes];
    return {
      mes,
      Saldo: Number(acumulado.toFixed(2)),
    };
  });

  // Pega apenas os últimos 6 meses
  return saldoAcumuladoPorMes.slice(-6);
}

// Gera dados para o PieChart de despesas por categoria
function getDespesasPorCategoria(movimentacoes: typeof mockMovimentacoes) {
  const categorias: { [cat: string]: number } = {};
  movimentacoes.forEach((mov) => {
    if (mov.valor < 0) {
      categorias[mov.categoria] = (categorias[mov.categoria] || 0) + Math.abs(mov.valor);
    }
  });
  return Object.entries(categorias).map(([name, value]) => ({ name, value }));
}

export default function Dashboard() {
  const [movimentacoes] = useState(mockMovimentacoes);
  const chartData = getSaldoAcumuladoPorMesUltimos6Meses(movimentacoes);
  const pieData = getDespesasPorCategoria(movimentacoes);

  return (
    <>
      <Navbar titulo="Dashboard" />
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          {/* Cards */}
          <DashboardCards />

          {/* Gráficos lado a lado */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-8">
            {/* Gráfico de saldo acumulado mês a mês */}
            <SaldoAcumuladoChart data={chartData} />
            {/* PieChart de despesas por categoria */}
            <DespesasPorCategoriaPie data={pieData} />
          </div>

          {/* Últimas movimentações */}
          <UltimasMovimentacoes movimentacoes={movimentacoes} />
        </div>
      </div>
    </>
  );
}