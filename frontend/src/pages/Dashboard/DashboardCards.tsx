import { Card } from "react-bootstrap";
import { FaWallet, FaArrowDown, FaChartLine, FaDollarSign } from "react-icons/fa";

export default function DashboardCards() {
  return (
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
          <FaDollarSign className="mx-auto mt-2 mb-2 text-2xl text-green-600" />
          <Card.Title className="text-sm text-gray-500">Receitas</Card.Title>
          <div className="text-xl font-bold text-green-600">R$ 2.000,00</div>
        </Card.Body>
      </Card>
      <Card className="text-center shadow-sm bg-white rounded">
        <Card.Body>
          <FaArrowDown className="mx-auto mt-2 mb-2 text-2xl text-red-600" />
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
  );
}