import { useState } from "react";
import { Eye, EyeOff, LockKeyhole, Mail, HeartPulse } from "lucide-react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });

    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.email || !formData.password) {
      setError("Please enter your email and password.");
      return;
    }

    try {
      setLoading(true);
      setError("");

      const response = await api.post("/api/auth/login", formData);

      const data = response.data;

      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("tokenType", data.tokenType);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("profileId", data.profileId);
      localStorage.setItem("email", data.email);
      localStorage.setItem("role", data.role);

      navigate("/dashboard");
    } catch (err) {
  console.log("LOGIN ERROR:", err);
  console.log("STATUS:", err.response?.status);
  console.log("RESPONSE:", err.response?.data);

  if (err.response?.data?.message) {
    setError(err.response.data.message);
  } else {
    setError(
      `Login failed. Status: ${err.response?.status || "No response"}`
    );
  }
} finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 flex items-center justify-center p-4">
      <div className="w-full max-w-5xl bg-white rounded-3xl shadow-2xl overflow-hidden grid md:grid-cols-2">

        {/* Left Side */}
        <div className="hidden md:flex bg-slate-900 text-white p-12 flex-col justify-between">
          <div>
            <div className="flex items-center gap-3">
              <div className="bg-white/10 p-3 rounded-xl">
                <HeartPulse size={28} />
              </div>

              <div>
                <h1 className="text-xl font-bold">
                  MediCare
                </h1>

                <p className="text-sm text-slate-400">
                  Hospital Administration
                </p>
              </div>
            </div>

            <div className="mt-20">
              <h2 className="text-4xl font-bold leading-tight">
                Smart Healthcare
                <br />
                Management
              </h2>

              <p className="mt-6 text-slate-400 leading-relaxed">
                Manage patients, doctors, appointments, billing and
                healthcare operations from one secure platform.
              </p>
            </div>
          </div>

          <p className="text-sm text-slate-500">
            © 2026 MediCare Hospital Administration System
          </p>
        </div>

        {/* Right Side */}
        <div className="p-8 sm:p-12 flex items-center">
          <div className="w-full max-w-md mx-auto">

            <div className="mb-8">
              <h2 className="text-3xl font-bold text-slate-900">
                Welcome Back
              </h2>

              <p className="mt-2 text-slate-500">
                Sign in to access your hospital dashboard.
              </p>
            </div>

            {error && (
              <div className="mb-5 rounded-lg bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-600">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-5">

              {/* Email */}
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Email Address
                </label>

                <div className="relative">
                  <Mail
                    size={19}
                    className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400"
                  />

                  <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Enter your email"
                    className="w-full rounded-xl border border-slate-200 bg-slate-50 py-3.5 pl-11 pr-4 outline-none transition focus:border-slate-900 focus:bg-white focus:ring-2 focus:ring-slate-900/10"
                  />
                </div>
              </div>

              {/* Password */}
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Password
                </label>

                <div className="relative">
                  <LockKeyhole
                    size={19}
                    className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400"
                  />

                  <input
                    type={showPassword ? "text" : "password"}
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter your password"
                    className="w-full rounded-xl border border-slate-200 bg-slate-50 py-3.5 pl-11 pr-12 outline-none transition focus:border-slate-900 focus:bg-white focus:ring-2 focus:ring-slate-900/10"
                  />

                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400 hover:text-slate-700"
                  >
                    {showPassword ? (
                      <EyeOff size={19} />
                    ) : (
                      <Eye size={19} />
                    )}
                  </button>
                </div>
              </div>

              {/* Login Button */}
              <button
                type="submit"
                disabled={loading}
                className="w-full rounded-xl bg-slate-900 py-3.5 font-semibold text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-60"
              >
                {loading ? "Signing in..." : "Sign In"}
              </button>

            </form>

            <p className="mt-8 text-center text-sm text-slate-500">
              Patient registration will be available here.
            </p>

          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
