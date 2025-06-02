import logoGraficoSemFundo from '../assets/logo_grafico_sem_fundo.png';

export default function Navbar() {
  //   const { logout } = useAuth();
  // const navigate = useNavigate();

  // const handleLogout = () => {
  //   logout();
  //   navigate("/");
  // };

  return (
    <nav className="container mx-auto flex justify-center items-center">
      <div className="flex items-center space-x-2">
        <img className="w-auto h-10" src={logoGraficoSemFundo} alt="Logo"/>
        Conta Simples MEI
      </div>
    </nav>
  );
}