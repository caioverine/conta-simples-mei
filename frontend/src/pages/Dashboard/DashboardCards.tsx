import { Card } from "react-bootstrap";
import { FaWallet, FaArrowDown, FaChartLine, FaDollarSign } from "react-icons/fa";
import type { DashboardResumoDTO } from "../../model/dashboard-resumo-model";

interface DashboardCardsProps {
  resumo: DashboardResumoDTO | null;
  loading: boolean;
}

export default function DashboardCards({ resumo, loading }: DashboardCardsProps) {
  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
      <Card className="text-center shadow-sm bg-white rounded">
        <Card.Body>
          <FaWallet className="mx-auto mt-2 mb-2 text-2xl text-[#234557]" />
          <Card.Title className="text-sm text-gray-500">Saldo</Card.Title>
          <div className="text-xl font-bold text-[#234557]">
            {loading ? "..." : `R$ ${resumo?.saldoAtual?.toLocaleString("pt-BR", { minimumFractionDigits: 2 }) ?? "0,00"}`}
          </div>
        </Card.Body>
      </Card>
      <Card className="text-center shadow-sm bg-white rounded">
        <Card.Body>
          <FaDollarSign className="mx-auto mt-2 mb-2 text-2xl text-green-600" />
          <Card.Title className="text-sm text-gray-500">Receitas</Card.Title>
          <div className="text-xl font-bold text-green-600">
            {loading ? "..." : `R$ ${resumo?.receitaTotal?.toLocaleString("pt-BR", { minimumFractionDigits: 2 }) ?? "0,00"}`}
          </div>
        </Card.Body>
      </Card>
      <Card className="text-center shadow-sm bg-white rounded">
        <Card.Body>
          <FaArrowDown className="mx-auto mt-2 mb-2 text-2xl text-red-600" />
          <Card.Title className="text-sm text-gray-500">Despesas</Card.Title>
          <div className="text-xl font-bold text-red-600">
            {loading ? "..." : `R$ ${resumo?.despesaTotal?.toLocaleString("pt-BR", { minimumFractionDigits: 2 }) ?? "0,00"}`}
          </div>
        </Card.Body>
      </Card>
      <Card className="text-center shadow-sm bg-white rounded">
        <Card.Body>
          <FaChartLine className="mx-auto mt-2 mb-2 text-2xl text-[#234557]" />
          <Card.Title className="text-sm text-gray-500">Resultado mensal</Card.Title>
          <div className="text-xl font-bold text-[#234557]">
            {loading ? "..." : `R$ ${resumo?.resultadoMensal?.toLocaleString("pt-BR", { minimumFractionDigits: 2 }) ?? "0,00"}`}
          </div>
        </Card.Body>
      </Card>
    </div>
  );
}