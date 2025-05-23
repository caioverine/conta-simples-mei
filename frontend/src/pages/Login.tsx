import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export default function Login() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/auth/login", {
                email: email,
                senha: senha,
            });

            login(response.data.token);

            navigate("/dashboard");
        } catch (err) {
            console.log(err);
            alert("Credenciais inválidas");
        }
    };

    return (
    <div className="flex items-center justify-center min-h-screen px-4">
      <div className="w-full max-w-xl p-8">
        <div className="flex-1">
          <div className="text-center">
            <div className="flex justify-center mb-4">
              <img className="w-auto h-7 sm:h-8" src="https://merakiui.com/images/logo.svg" alt=""/>
            </div>

            <p className="mt-3 text-gray-500 dark:text-gray-300">Sign in to access your account</p>
          </div>

          <div className="mt-8">
            <form onSubmit={handleLogin}>
              <div>
                <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Email Address</label>
                <input type="email" name="email" id="email" placeholder="example@example.com" value={email} onChange={(e) => setEmail(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
              </div>

              <div className="mt-6">
                <div className="flex justify-between mb-2">
                  <label className="text-sm text-gray-600 dark:text-gray-200">Password</label>
                  <a href="#" className="text-sm text-gray-400 focus:text-blue-500 hover:text-blue-500 hover:underline">Forgot password?</a>
                </div>

                <input type="password" name="password" id="password" placeholder="Your Password" value={senha} onChange={(e) => setSenha(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
              </div>

              <div className="mt-6">
                <button type="submit" 
                  className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-300 transform bg-blue-500 rounded-lg hover:bg-blue-400 focus:outline-none focus:bg-blue-400 focus:ring focus:ring-blue-300 focus:ring-opacity-50">
                  Sign in
                </button>
              </div>

            </form>

            <p className="mt-6 text-sm text-center text-gray-400">Don&#x27;t have an account yet? <a href="#" 
              className="text-blue-500 focus:outline-none focus:underline hover:underline">Sign up</a>.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
