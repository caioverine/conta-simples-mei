import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";

interface SaldoAcumuladoChartProps {
  data: { mes: string; saldo: number }[];
}

export default function SaldoAcumuladoChart({ data }: SaldoAcumuladoChartProps) {
  return (
    <div className="bg-white rounded shadow p-6">
      <h3 className="text-lg font-semibold text-[#234557] mb-4">Evolução do saldo acumulado mês a mês</h3>
      <ResponsiveContainer width="100%" height={250}>
        <LineChart data={data}>
          <XAxis dataKey="mes" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="saldo" stroke="#234557" strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}