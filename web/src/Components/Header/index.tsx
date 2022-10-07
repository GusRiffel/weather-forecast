export function Header() {
  return (
    <div className="grid grid-cols-3 bg-slate-300 h-20 items-center rounded ">
      <div></div>
      <div className="text-center text-3xl">
        <h1>Welcome to Weather Forecast</h1>
      </div>
      <div className="text-right text-xl pr-5 cursor-pointer">
        Login
      </div>
    </div>
  );
}
