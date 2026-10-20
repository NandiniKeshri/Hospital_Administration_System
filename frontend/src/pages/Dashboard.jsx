
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../api";
import {
  LayoutDashboard, Users, Stethoscope, CalendarDays, ClipboardList,
  Pill, CreditCard, Settings, Menu, X, Search, Bell, ChevronDown,
  ArrowUpRight, ArrowDownRight, Clock, CheckCircle2, AlertCircle,
  Plus, Download, Filter, Activity, HeartPulse, BedDouble, UserRound,
  LogOut, FileText, ShieldCheck, MoreHorizontal
} from "lucide-react";

const navigation = [
  { label: "Dashboard", path: "/dashboard", icon: LayoutDashboard },
  { label: "Patients", path: "/patients", icon: Users },
  { label: "Doctors", path: "/doctors", icon: Stethoscope },
  { label: "Appointments", path: "/appointments", icon: CalendarDays },
  { label: "Departments", path: "/departments", icon: ClipboardList },
  { label: "Pharmacy", path: "/pharmacy", icon: Pill },
  { label: "Billing", path: "/billing", icon: CreditCard },
  { label: "Settings", path: "/settings", icon: Settings },
];

const appointments = [
  { id: "APT-1001", patient: "Aarav Sharma", doctor: "Dr. Meera Kapoor", department: "Cardiology", date: "11 Oct 2026", time: "09:00 AM", status: "Confirmed" },
  { id: "APT-1002", patient: "Priya Verma", doctor: "Dr. Rahul Singh", department: "Neurology", date: "11 Oct 2026", time: "10:30 AM", status: "Pending" },
  { id: "APT-1003", patient: "Rohan Gupta", doctor: "Dr. Ananya Das", department: "Orthopedics", date: "11 Oct 2026", time: "11:00 AM", status: "Confirmed" },
  { id: "APT-1004", patient: "Simran Kaur", doctor: "Dr. Meera Kapoor", department: "Cardiology", date: "11 Oct 2026", time: "12:15 PM", status: "Cancelled" },
];

const doctors = [
  { id: "DOC-101", name: "Dr. Meera Kapoor", specialty: "Cardiology", experience: "12 years", phone: "+91 98765 20001", status: "Available" },
  { id: "DOC-102", name: "Dr. Rahul Singh", specialty: "Neurology", experience: "9 years", phone: "+91 98765 20002", status: "Available" },
  { id: "DOC-103", name: "Dr. Ananya Das", specialty: "Orthopedics", experience: "8 years", phone: "+91 98765 20003", status: "Busy" },
];

const departments = [
  { name: "Cardiology", head: "Dr. Meera Kapoor", doctors: 8, beds: 24, status: "Operational" },
  { name: "Neurology", head: "Dr. Rahul Singh", doctors: 6, beds: 18, status: "Operational" },
  { name: "Orthopedics", head: "Dr. Ananya Das", doctors: 7, beds: 22, status: "Operational" },
];

const medicines = [
  { id: "MED-001", name: "Paracetamol 500mg", category: "Pain relief", stock: 240, price: "₹25", status: "In Stock" },
  { id: "MED-002", name: "Amoxicillin 250mg", category: "Antibiotic", stock: 18, price: "₹85", status: "Low Stock" },
  { id: "MED-003", name: "Cetirizine 10mg", category: "Antihistamine", stock: 150, price: "₹40", status: "In Stock" },
];

const bills = [
  { id: "INV-2001", patient: "Aarav Sharma", date: "10 Oct 2026", amount: "₹4,500", status: "Paid" },
  { id: "INV-2002", patient: "Priya Verma", date: "10 Oct 2026", amount: "₹2,200", status: "Pending" },
  { id: "INV-2003", patient: "Rohan Gupta", date: "09 Oct 2026", amount: "₹6,800", status: "Paid" },
];

const weeklyData = [
  { day: "Mon", value: 42 }, { day: "Tue", value: 68 },
  { day: "Wed", value: 53 }, { day: "Thu", value: 84 },
  { day: "Fri", value: 62 }, { day: "Sat", value: 92 },
  { day: "Sun", value: 47 },
];

const recentActivity = [
  { title: "Patient records", detail: "Review recently registered patients", time: "Recent", icon: Users },
  { title: "Appointments", detail: "Check appointment schedules", time: "Today", icon: CalendarDays },
  { title: "Billing", detail: "Review invoice status", time: "Today", icon: CreditCard },
  { title: "Pharmacy", detail: "Check medicine stock", time: "Today", icon: Pill },
];

function StatusBadge({ status }) {
  const styles = {
    Confirmed: "bg-emerald-50 text-emerald-700",
    Active: "bg-emerald-50 text-emerald-700",
    Available: "bg-emerald-50 text-emerald-700",
    Paid: "bg-emerald-50 text-emerald-700",
    Operational: "bg-emerald-50 text-emerald-700",
    "In Stock": "bg-emerald-50 text-emerald-700",
    Pending: "bg-amber-50 text-amber-700",
    "Low Stock": "bg-amber-50 text-amber-700",
    Busy: "bg-amber-50 text-amber-700",
    Overdue: "bg-rose-50 text-rose-700",
    Cancelled: "bg-rose-50 text-rose-700",
    Inactive: "bg-slate-100 text-slate-600",
  };

  return (
    <span className={`inline-flex whitespace-nowrap rounded-full px-2.5 py-1 text-xs font-semibold ${styles[status] || "bg-slate-100 text-slate-600"}`}>
      {status || "Unknown"}
    </span>
  );
}

function SectionTitle({ title, description, action, onAction }) {
  return (
    <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
      <div>
        <h2 className="text-xl font-bold text-slate-900">{title}</h2>
        <p className="mt-1 text-sm text-slate-500">{description}</p>
      </div>
      {action && (
        <button onClick={onAction} className="inline-flex items-center justify-center gap-2 rounded-xl bg-teal-700 px-4 py-2.5 text-sm font-semibold text-white hover:bg-teal-800">
          <Plus size={17} /> {action}
        </button>
      )}
    </div>
  );
}

function MiniStat({ title, value, icon: Icon }) {
  return (
    <div className="flex items-center gap-4 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
      <span className="rounded-xl bg-teal-50 p-3 text-teal-700"><Icon size={21} /></span>
      <div>
        <p className="text-sm text-slate-500">{title}</p>
        <p className="mt-1 text-xl font-bold text-slate-900">{value}</p>
      </div>
    </div>
  );
}

function EmptySearch({ query }) {
  return (
    <div className="px-5 py-12 text-center">
      <Search className="mx-auto mb-3 text-slate-300" size={32} />
      <p className="font-semibold text-slate-700">No matching records</p>
      <p className="mt-1 text-sm text-slate-500">No results found for "{query}".</p>
    </div>
  );
}

function DataTable({ columns, rows, query }) {
  if (!rows.length) return <EmptySearch query={query} />;

  return (
    <div className="overflow-x-auto">
      <table className="w-full min-w-[720px] text-left text-sm">
        <thead className="bg-slate-50 text-xs uppercase tracking-wide text-slate-500">
          <tr>
            {columns.map((column) => <th key={column.key} className="px-5 py-4 font-semibold">{column.label}</th>)}
            <th className="px-5 py-4 font-semibold">Action</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {rows.map((row, index) => (
            <tr key={row.id || row.name || index} className="transition hover:bg-slate-50">
              {columns.map((column) => (
                <td key={column.key} className="px-5 py-4 text-slate-600">
                  {column.render ? column.render(row[column.key], row) : (row[column.key] ?? "—")}
                </td>
              ))}
              <td className="px-5 py-4">
                <button type="button" title="Record options" onClick={() => window.alert(`Record: ${row.id || row.name || "Details"}`)} className="rounded-lg p-2 text-slate-400 hover:bg-slate-100">
                  <MoreHorizontal size={19} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function DashboardHome({ onNavigate, patientCount }) {
  const stats = [
    { title: "Total Patients", value: String(patientCount), change: "Live count", icon: Users, iconStyle: "bg-teal-50 text-teal-700" },
    { title: "Total Doctors", value: "3", change: "Demo data", icon: Stethoscope, iconStyle: "bg-blue-50 text-blue-700" },
    { title: "Appointments", value: String(appointments.length), change: "Demo data", icon: CalendarDays, iconStyle: "bg-violet-50 text-violet-700" },
    { title: "Available Beds", value: "72", change: "Demo data", icon: BedDouble, iconStyle: "bg-orange-50 text-orange-700" },
  ];

  return (
    <>
      <section className="relative overflow-hidden rounded-3xl bg-gradient-to-r from-teal-800 via-teal-700 to-cyan-700 p-6 text-white shadow-lg sm:p-8">
        <div className="relative z-10 max-w-2xl">
          <p className="text-sm text-teal-100">Hospital Administration</p>
          <h2 className="mt-3 text-2xl font-bold sm:text-3xl">Welcome to MediCare!</h2>
          <p className="mt-3 max-w-lg text-sm leading-6 text-teal-50 sm:text-base">
            Manage patient records, appointments and hospital operations from one place.
          </p>
          <div className="mt-6 flex flex-wrap gap-3">
            <button onClick={() => onNavigate("Appointments")} className="inline-flex items-center gap-2 rounded-xl bg-white px-4 py-2.5 text-sm font-semibold text-teal-800">
              <CalendarDays size={17} /> View appointments
            </button>
            <button onClick={() => onNavigate("Patients")} className="inline-flex items-center gap-2 rounded-xl border border-white/30 px-4 py-2.5 text-sm font-semibold">
              <Users size={17} /> Manage patients
            </button>
          </div>
        </div>
        <HeartPulse className="pointer-events-none absolute bottom-5 right-8 hidden text-white/15 sm:block" size={150} strokeWidth={1} />
      </section>

      <section className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4">
        {stats.map((stat) => {
          const Icon = stat.icon;
          return (
            <div key={stat.title} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
              <div className="flex items-center justify-between">
                <span className={`rounded-xl p-3 ${stat.iconStyle}`}><Icon size={22} /></span>
                <span className="text-xs font-semibold text-slate-500">{stat.change}</span>
              </div>
              <p className="mt-5 text-sm font-medium text-slate-500">{stat.title}</p>
              <p className="mt-1 text-3xl font-bold text-slate-900">{stat.value}</p>
            </div>
          );
        })}
      </section>

      <section className="grid grid-cols-1 gap-6 xl:grid-cols-3">
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm sm:p-6 xl:col-span-2">
          <h3 className="font-bold text-slate-900">Patient Overview</h3>
          <p className="mt-1 text-sm text-slate-500">Weekly patient visits · demo chart</p>
          <div className="mt-8 flex h-56 items-end justify-between gap-3 border-b border-slate-100 px-1 sm:gap-5">
            {weeklyData.map((item) => (
              <div key={item.day} className="flex h-full flex-1 flex-col items-center justify-end gap-3">
                <span className="text-xs text-slate-500">{item.value}</span>
                <div className={`w-full max-w-12 rounded-t-lg ${item.day === "Sat" ? "bg-teal-700" : "bg-teal-100"}`} style={{ height: `${item.value * 1.45}px`, maxHeight: "80%" }} />
                <span className="pb-3 text-xs text-slate-500">{item.day}</span>
              </div>
            ))}
          </div>
        </div>
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
          <h3 className="font-bold text-slate-900">Quick Actions</h3>
          <p className="mt-1 text-sm text-slate-500">Common tasks</p>
          <div className="mt-5 space-y-3">
            {[
              { label: "Manage Patients", detail: "View patient records", icon: Users, page: "Patients" },
              { label: "Appointments", detail: "Check schedules", icon: CalendarDays, page: "Appointments" },
              { label: "Doctors", detail: "View medical staff", icon: Stethoscope, page: "Doctors" },
              { label: "Billing", detail: "Review invoices", icon: CreditCard, page: "Billing" },
            ].map((item) => {
              const Icon = item.icon;
              return (
                <button key={item.label} onClick={() => onNavigate(item.page)} className="group flex w-full items-center gap-3 rounded-xl border border-slate-100 p-3 text-left hover:border-teal-200 hover:bg-teal-50/60">
                  <span className="rounded-lg bg-teal-50 p-2.5 text-teal-700"><Icon size={19} /></span>
                  <span className="min-w-0 flex-1">
                    <span className="block text-sm font-semibold text-slate-800">{item.label}</span>
                    <span className="mt-0.5 block text-xs text-slate-500">{item.detail}</span>
                  </span>
                  <ArrowUpRight size={17} className="text-slate-400" />
                </button>
              );
            })}
          </div>
        </div>
      </section>

      <section className="grid grid-cols-1 gap-6 xl:grid-cols-2">
        <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
          <div className="p-5">
            <h3 className="font-bold text-slate-900">Recent Appointments</h3>
            <p className="mt-1 text-sm text-slate-500">Demo appointment records</p>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-[550px] text-left text-sm">
              <thead className="bg-slate-50 text-xs uppercase text-slate-500">
                <tr><th className="px-5 py-3">Patient</th><th className="px-5 py-3">Doctor</th><th className="px-5 py-3">Time</th><th className="px-5 py-3">Status</th></tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {appointments.slice(0, 4).map((item) => (
                  <tr key={item.id}>
                    <td className="px-5 py-4"><p className="font-semibold">{item.patient}</p><p className="text-xs text-slate-400">{item.id}</p></td>
                    <td className="px-5 py-4">{item.doctor}</td>
                    <td className="px-5 py-4">{item.time}</td>
                    <td className="px-5 py-4"><StatusBadge status={item.status} /></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
          <h3 className="font-bold text-slate-900">Recent Activity</h3>
          <div className="mt-5 space-y-5">
            {recentActivity.map((item) => {
              const Icon = item.icon;
              return (
                <div key={item.title} className="flex gap-3">
                  <span className="rounded-xl bg-teal-50 p-2.5 text-teal-700"><Icon size={18} /></span>
                  <div><p className="text-sm font-semibold">{item.title}</p><p className="mt-1 text-xs text-slate-500">{item.detail}</p><p className="mt-1 text-xs text-slate-400">{item.time}</p></div>
                </div>
              );
            })}
          </div>
        </div>
      </section>
    </>
  );
}

function ModulePage({ page, query, onAction, patients, patientsLoading, patientsError }) {
  const configs = {
    Patients: {
      title: "Patient Management",
      description: "View and manage patient profiles and records.",
      action: "Add Patient",
      columns: [
        { key: "id", label: "Patient ID" },
        {
          key: "name", label: "Patient",
          render: (value, row) => (
            <div className="flex items-center gap-3">
              <span className="flex h-9 w-9 items-center justify-center rounded-full bg-teal-50 font-bold text-teal-700">
                {(value || "U").split(" ").map((part) => part[0]).join("").slice(0, 2)}
              </span>
              <div><p className="font-semibold text-slate-800">{value || "Unknown Patient"}</p><p className="text-xs text-slate-400">{row.gender}, {row.age} years</p></div>
            </div>
          ),
        },
        { key: "phone", label: "Phone" },
        { key: "condition", label: "Reason / Condition" },
        { key: "status", label: "Status", render: (value) => <StatusBadge status={value} /> },
      ],
      rows: patients,
    },
    Doctors: {
      title: "Doctor Management", description: "Manage doctor profiles and availability.", action: "Add Doctor",
      columns: [{ key: "id", label: "Doctor ID" }, { key: "name", label: "Doctor Name" }, { key: "specialty", label: "Specialty" }, { key: "experience", label: "Experience" }, { key: "phone", label: "Phone" }, { key: "status", label: "Status", render: (value) => <StatusBadge status={value} /> }],
      rows: doctors,
    },
    Appointments: {
      title: "Appointment Management", description: "Review appointment schedules and booking status.", action: "New Appointment",
      columns: [{ key: "id", label: "Appointment ID" }, { key: "patient", label: "Patient" }, { key: "doctor", label: "Doctor" }, { key: "department", label: "Department" }, { key: "date", label: "Date" }, { key: "time", label: "Time" }, { key: "status", label: "Status", render: (value) => <StatusBadge status={value} /> }],
      rows: appointments,
    },
    Departments: {
      title: "Department Management", description: "View hospital departments and bed capacity.", action: "Add Department",
      columns: [{ key: "name", label: "Department" }, { key: "head", label: "Department Head" }, { key: "doctors", label: "Doctors" }, { key: "beds", label: "Beds" }, { key: "status", label: "Status", render: (value) => <StatusBadge status={value} /> }],
      rows: departments,
    },
    Pharmacy: {
      title: "Pharmacy Management", description: "Monitor medicine inventory and stock availability.", action: "Add Medicine",
      columns: [{ key: "id", label: "Medicine ID" }, { key: "name", label: "Medicine" }, { key: "category", label: "Category" }, { key: "stock", label: "Stock" }, { key: "price", label: "Price" }, { key: "status", label: "Status", render: (value) => <StatusBadge status={value} /> }],
      rows: medicines,
    },
    Billing: {
      title: "Billing & Invoices", description: "Track invoices and payment status.", action: "Create Invoice",
      columns: [{ key: "id", label: "Invoice ID" }, { key: "patient", label: "Patient" }, { key: "date", label: "Invoice Date" }, { key: "amount", label: "Amount" }, { key: "status", label: "Payment Status", render: (value) => <StatusBadge status={value} /> }],
      rows: bills,
    },
  };

  if (page === "Settings") {
    return (
      <div className="space-y-6">
        <SectionTitle title="Settings" description="Manage your profile and application preferences." />
        <div className="grid grid-cols-1 gap-5 lg:grid-cols-2">
          {[
            { icon: UserRound, title: "Profile Information", detail: "Manage your account and profile details." },
            { icon: Bell, title: "Notifications", detail: "Review notification preferences." },
            { icon: ShieldCheck, title: "Security", detail: "Manage account security settings." },
            { icon: FileText, title: "Hospital Information", detail: "Configure hospital details." },
          ].map((item) => {
            const Icon = item.icon;
            return <div key={item.title} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm"><Icon className="mb-3 text-teal-700" size={23} /><h3 className="font-bold">{item.title}</h3><p className="mt-2 text-sm text-slate-500">{item.detail}</p><button onClick={() => onAction(item.title)} className="mt-4 text-sm font-semibold text-teal-700">Manage settings →</button></div>;
          })}
        </div>
      </div>
    );
  }

  const config = configs[page];
  if (!config) return null;

  if (page === "Patients" && patientsLoading) {
    return <div className="rounded-2xl border border-slate-200 bg-white p-10 text-center"><p className="font-semibold text-slate-700">Loading patients...</p></div>;
  }

  if (page === "Patients" && patientsError) {
    return <div className="rounded-2xl border border-rose-200 bg-rose-50 p-5 text-rose-700"><p className="font-semibold">Unable to load patients</p><p className="mt-1 text-sm">{patientsError}</p><button onClick={() => window.location.reload()} className="mt-3 text-sm font-semibold underline">Reload page</button></div>;
  }

  const filteredRows = config.rows.filter((row) =>
    Object.values(row).some((value) => String(value ?? "").toLowerCase().includes(query.toLowerCase()))
  );

  return (
    <div>
      <SectionTitle title={config.title} description={config.description} action={config.action} onAction={() => onAction(config.action)} />
      {page === "Patients" && <div className="mb-5 grid grid-cols-1 gap-4 sm:grid-cols-3"><MiniStat title="Total Patients" value={String(patients.length)} icon={Users} /><MiniStat title="Active Patients" value={String(patients.filter((p) => p.status === "Active").length)} icon={CheckCircle2} /><MiniStat title="Loaded from API" value="Live data" icon={Activity} /></div>}
      {page === "Doctors" && <div className="mb-5 grid grid-cols-1 gap-4 sm:grid-cols-3"><MiniStat title="Total Doctors" value={String(doctors.length)} icon={Stethoscope} /><MiniStat title="Available Now" value={String(doctors.filter((d) => d.status === "Available").length)} icon={CheckCircle2} /><MiniStat title="Departments" value={String(departments.length)} icon={ClipboardList} /></div>}
      <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
        <div className="flex flex-col gap-4 border-b border-slate-100 p-5 sm:flex-row sm:items-center sm:justify-between">
          <div><h3 className="font-bold text-slate-900">All {page}</h3><p className="mt-1 text-sm text-slate-500">{filteredRows.length} record{filteredRows.length === 1 ? "" : "s"} found</p></div>
          <div className="flex gap-2">
            <button onClick={() => onAction("Filter")} className="inline-flex items-center gap-2 rounded-xl border border-slate-200 px-3 py-2.5 text-sm"><Filter size={16} /> Filter</button>
            <button onClick={() => onAction("Export")} className="inline-flex items-center gap-2 rounded-xl border border-slate-200 px-3 py-2.5 text-sm"><Download size={16} /> Export</button>
          </div>
        </div>
        <DataTable columns={config.columns} rows={filteredRows} query={query} />
        <div className="border-t border-slate-100 px-5 py-4 text-xs text-slate-500">
          {page === "Patients" ? "Patient records returned by the backend API." : "Demo records — backend integration is not configured for this module."}
        </div>
      </div>
    </div>
  );
}

export default function Dashboard() {
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [search, setSearch] = useState("");
  const [notice, setNotice] = useState("");
  const [patients, setPatients] = useState([]);
  const [patientsLoading, setPatientsLoading] = useState(true);
  const [patientsError, setPatientsError] = useState("");

  useEffect(() => {
    let cancelled = false;

    async function fetchPatients() {
      try {
        const response = await api.get("/patients");
        const data = response.data;
        const list = Array.isArray(data)
          ? data
          : Array.isArray(data?.content)
          ? data.content
          : Array.isArray(data?.data)
          ? data.data
          : [];

        const normalized = list.map((p, index) => ({
          id: p.patientId ?? p.profileId ?? p.id ?? p.userId ?? `PAT-${index + 1}`,
          name: p.fullName ?? p.name ?? [p.firstName, p.lastName].filter(Boolean).join(" ") ?? p.email ?? "Unknown Patient",
          age: p.age ?? "—",
          gender: p.gender ?? "—",
          phone: p.phone ?? p.phoneNumber ?? p.mobileNumber ?? "—",
          condition: p.condition ?? p.reason ?? "—",
          status: p.status ?? "Active",
        }));

        if (!cancelled) {
          setPatients(normalized);
          setPatientsError("");
        }
      } catch (error) {
        console.error("Failed to fetch patients:", error);
        if (!cancelled) {
          setPatientsError(
            error.response?.status === 401
              ? "Please log in to view patients."
              : error.response?.status === 403
              ? "You do not have permission to view patients."
              : "Unable to load patients. Verify that GET /api/patients exists and the backend is running."
          );
        }
      } finally {
        if (!cancelled) setPatientsLoading(false);
      }
    }

    fetchPatients();
    return () => { cancelled = true; };
  }, []);

  const pageNames = {
    "/dashboard": "Dashboard",
    "/patients": "Patients",
    "/doctors": "Doctors",
    "/appointments": "Appointments",
    "/departments": "Departments",
    "/pharmacy": "Pharmacy",
    "/billing": "Billing",
    "/settings": "Settings",
  };
  const activePage = pageNames[pathname] || "Dashboard";

  function handleNavigation(label) {
    const item = navigation.find((nav) => nav.label === label);
    if (item) {
      navigate(item.path);
      setSidebarOpen(false);
      setSearch("");
      setNotice("");
    }
  }

  function handleAction(action) {
    setNotice(`${action}: the interface is ready. Backend save/update functionality is not yet connected for this action.`);
  }

  return (
    <div className="min-h-screen bg-[#f5f7fb] text-slate-800">
      {sidebarOpen && <button aria-label="Close sidebar" onClick={() => setSidebarOpen(false)} className="fixed inset-0 z-40 bg-slate-950/40 lg:hidden" />}

      <aside className={`fixed inset-y-0 left-0 z-50 flex w-[258px] flex-col border-r border-slate-200 bg-white transition-transform duration-300 ${sidebarOpen ? "translate-x-0" : "-translate-x-full"} lg:translate-x-0`}>
        <div className="flex h-[82px] items-center justify-between border-b border-slate-100 px-6">
          <button onClick={() => handleNavigation("Dashboard")} className="flex items-center gap-3 text-left">
            <span className="flex h-11 w-11 items-center justify-center rounded-2xl bg-teal-700 text-white"><HeartPulse size={25} /></span>
            <span><span className="block text-lg font-extrabold text-slate-900">MediCare</span><span className="block text-[10px] font-semibold uppercase tracking-[0.2em] text-teal-700">Hospital System</span></span>
          </button>
          <button onClick={() => setSidebarOpen(false)} className="rounded-lg p-2 text-slate-500 hover:bg-slate-100 lg:hidden" aria-label="Close menu"><X size={20} /></button>
        </div>

        <div className="px-4 pt-7">
          <p className="mb-3 px-3 text-[10px] font-bold uppercase tracking-[0.18em] text-slate-400">Main Menu</p>
          <nav className="space-y-1.5">
            {navigation.map((item) => {
              const Icon = item.icon;
              const active = activePage === item.label;
              return <button key={item.label} onClick={() => handleNavigation(item.label)} className={`group flex w-full items-center gap-3 rounded-xl px-3.5 py-3 text-sm font-semibold transition ${active ? "bg-teal-700 text-white shadow-md" : "text-slate-500 hover:bg-teal-50 hover:text-teal-800"}`}><Icon size={19} /><span className="flex-1 text-left">{item.label}</span>{active && <span className="h-1.5 w-1.5 rounded-full bg-white" />}</button>;
            })}
          </nav>
        </div>

        <div className="mt-auto p-4">
          <div className="rounded-2xl bg-gradient-to-br from-teal-50 to-cyan-50 p-4">
            <div className="flex items-center gap-2 text-teal-800"><ShieldCheck size={19} /><span className="text-sm font-bold">System Status</span></div>
            <p className="mt-2 text-xs leading-5 text-slate-600">Keep patient information secure and up to date.</p>
            <div className="mt-3 flex items-center gap-2 text-xs font-semibold text-emerald-700"><span className="h-2 w-2 rounded-full bg-emerald-500" /> Dashboard interface active</div>
          </div>
          <button onClick={() => navigate("/login")} className="mt-4 flex w-full items-center gap-3 rounded-xl px-3.5 py-3 text-sm font-semibold text-slate-500 hover:bg-rose-50 hover:text-rose-600"><LogOut size={19} /> Back to Login</button>
          <p className="mt-4 text-center text-[10px] text-slate-400">MediCare Hospital System</p>
        </div>
      </aside>

      <main className="min-h-screen lg:ml-[258px]">
        <header className="sticky top-0 z-30 border-b border-slate-200/80 bg-white/90 backdrop-blur-xl">
          <div className="flex min-h-[82px] items-center justify-between gap-4 px-4 sm:px-7 lg:px-9">
            <div className="flex min-w-0 items-center gap-3">
              <button onClick={() => setSidebarOpen(true)} className="rounded-xl border border-slate-200 p-2.5 text-slate-600 lg:hidden" aria-label="Open menu"><Menu size={20} /></button>
              <div className="min-w-0"><div className="flex items-center gap-2 text-xs text-slate-400"><span>Hospital</span><span>/</span><span className="truncate font-medium text-teal-700">{activePage}</span></div><h1 className="mt-1 truncate text-xl font-bold text-slate-900 sm:text-2xl">{activePage}</h1></div>
            </div>
            <div className="flex items-center gap-2 sm:gap-4">
              <div className="relative hidden w-52 md:block xl:w-64"><Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" size={17} /><input type="search" value={search} onChange={(event) => setSearch(event.target.value)} placeholder="Search records..." className="w-full rounded-xl border border-slate-200 bg-slate-50 py-2.5 pl-9 pr-3 text-sm outline-none focus:border-teal-500 focus:bg-white" /></div>
              <button onClick={() => setNotice("Notifications: no live notification API is connected yet.")} className="relative rounded-xl border border-slate-200 p-2.5 text-slate-500" aria-label="Notifications"><Bell size={19} /></button>
              <div className="hidden h-9 w-px bg-slate-200 sm:block" />
              <button onClick={() => handleNavigation("Settings")} className="flex items-center gap-2.5 rounded-xl p-1.5 text-left hover:bg-slate-50"><span className="flex h-10 w-10 items-center justify-center rounded-full bg-teal-100 text-sm font-bold text-teal-800">NK</span><span className="hidden sm:block"><span className="block text-sm font-bold">Admin User</span><span className="block text-xs text-slate-500">Administrator</span></span><ChevronDown size={16} className="hidden text-slate-400 sm:block" /></button>
            </div>
          </div>
        </header>

        <div className="space-y-7 p-4 sm:p-7 lg:p-9">
          {notice && <div role="status" className="flex items-start justify-between gap-3 rounded-xl border border-teal-200 bg-teal-50 px-4 py-3 text-sm text-teal-900"><p>{notice}</p><button onClick={() => setNotice("")} aria-label="Dismiss notice"><X size={17} /></button></div>}
          <div className="relative md:hidden"><Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" size={17} /><input type="search" value={search} onChange={(event) => setSearch(event.target.value)} placeholder={`Search ${activePage.toLowerCase()}...`} className="w-full rounded-xl border border-slate-200 bg-white py-3 pl-9 pr-3 text-sm outline-none focus:border-teal-500" /></div>

          {activePage === "Dashboard" ? (
            <DashboardHome onNavigate={handleNavigation} patientCount={patients.length} />
          ) : (
            <ModulePage page={activePage} query={search} onAction={handleAction} patients={patients} patientsLoading={patientsLoading} patientsError={patientsError} />
          )}

          <footer className="flex flex-col items-center justify-between gap-2 border-t border-slate-200 pt-5 text-xs text-slate-400 sm:flex-row">
            <p>© 2026 MediCare Hospital Administration System</p>
            <span className="inline-flex items-center gap-1.5"><ShieldCheck size={14} /> Patient privacy matters · Version 1.0</span>
          </footer>
        </div>
      </main>
    </div>
  );
}