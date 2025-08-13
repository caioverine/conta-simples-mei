import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Link } from 'react-router-dom';
import { useAuth } from "../contexts/useAuth";
import { realizarLogin } from "../services/login-service";

export default function Login() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const navigate = useNavigate();
    const { login } = useAuth();
    const location = useLocation();

    // Pega a rota que o usuário estava tentando acessar
    const from = location.state?.from?.pathname || '/';

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await realizarLogin(email, senha);

            login(response.data.token);

            navigate(from, { replace: true });
        } catch (err) {
            console.log(err);
            alert("Credenciais inválidas");
        }
    };

    return (
      <>
        <div className="flex flex-col justify-center items-center min-h-screen px-2 sm:px-4 bg-white dark:bg-gray-900">
          <div className="w-full max-w-md sm:max-w-lg md:max-w-xl p-6 sm:p-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
            <div className="flex-1">
              <div className="mt-4 sm:mt-8">
                <form onSubmit={handleLogin}>
                  <div>
                    <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Email</label>
                    <input
                      type="email"
                      name="email"
                      id="email"
                      placeholder="usuario@mail.com"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                    />
                  </div>

                  <div className="mt-6">
                    <div className="flex justify-between mb-2">
                      <label className="text-sm text-gray-600 dark:text-gray-200">Senha</label>
                      <a href="#" className="hover:underline">Esqueci a senha</a>
                    </div>

                    <input
                      type="password"
                      name="password"
                      id="password"
                      placeholder="Sua senha"
                      value={senha}
                      onChange={(e) => setSenha(e.target.value)}
                      className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                    />
                  </div>

                  <div className="mt-6">
                    <button
                      type="submit"
                      className="btn w-full px-4 py-2 tracking-wide"
                    >
                      Entrar
                    </button>
                  </div>
                </form>

                <p className="mt-6 text-sm text-center text-gray-400">
                  Ainda não tem conta?
                  <Link to="/cadastro" className="focus:outline-none focus:underline hover:underline"> Cadastre-se</Link>.
                </p>
              </div>
            </div>
          </div>
        </div>
      </>
    );
}
