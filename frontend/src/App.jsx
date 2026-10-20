import { useEffect, useState } from 'react';

const FLIGHT_API = 'https://skybook-airline-reservation-0anr.onrender.com/api/flights';
const BOOKING_API = 'https://skybook-airline-reservation-9nkc.onrender.com/api/bookings';
export default function App() {
  const [flights,setFlights] = useState([]);
  const [bookings,setBookings] = useState([]);
  const [from,setFrom] = useState('');
  const [to,setTo] = useState('');
  const [passengerName,setPassengerName] = useState('');
  const [passengerEmail,setPassengerEmail] = useState('');
  const [selected,setSelected] = useState(null);
  const [message,setMessage] = useState('');
  const [error,setError] = useState('');

  async function loadFlights() {
    setError('');
    try {
      const query = new URLSearchParams();
      if(from.trim()) query.set('from',from.trim());
      if(to.trim()) query.set('to',to.trim());
      const response = await fetch(`${FLIGHT_API}?${query}`);
      if(!response.ok) throw new Error('Could not load flights. Is Flight Service running?');
      setFlights(await response.json());
    } catch(e) { setError(e.message); }
  }
  async function loadBookings() {
    try {
      const response=await fetch(BOOKING_API);
      if(response.ok) setBookings(await response.json());
    } catch { /* service may still be starting */ }
  }
  useEffect(()=>{loadFlights();loadBookings();},[]);

  async function bookFlight(event) {
    event.preventDefault(); setMessage('');setError('');
    if(!selected) return setError('Choose a flight first.');
    try {
      const response=await fetch(BOOKING_API,{method:'POST',headers:{'Content-Type':'application/json'},
        body:JSON.stringify({flightId:selected.id,passengerName,passengerEmail})});
      const data=await response.json();
      if(!response.ok) throw new Error(data.message || 'Booking failed.');
      setMessage(`Booking confirmed! Your booking ID is ${data.id}.`);
      setPassengerName('');setPassengerEmail('');setSelected(null);loadBookings();
    } catch(e){setError(e.message);}
  }
  async function cancelBooking(id) {
    setError('');setMessage('');
    try {
      const response=await fetch(`${BOOKING_API}/${id}`,{method:'DELETE'});
      if(!response.ok) throw new Error('Could not cancel booking.');
      setMessage(`Booking ${id} cancelled.`);loadBookings();
    } catch(e){setError(e.message);}
  }

  return <div className="app">
    <header className="hero">
      <div className="brand">✈ SkyBook</div>
      <div className="heroText"><p className="eyebrow">YOUR JOURNEY STARTS HERE</p><h1>Find your next flight.</h1><p>Search flights and manage your demo reservations.</p></div>
      <div className="plane">✈</div>
    </header>
    <main>
      {message && <div className="notice success">{message}</div>}
      {error && <div className="notice error">{error}</div>}
      <section className="panel search">
        <div><h2>Search flights</h2><p className="muted">Try Chennai → Delhi or Chennai → Mumbai.</p></div>
        <form className="searchForm" onSubmit={e=>{e.preventDefault();loadFlights();}}>
          <label>From<input value={from} onChange={e=>setFrom(e.target.value)} placeholder="Any origin"/></label>
          <label>To<input value={to} onChange={e=>setTo(e.target.value)} placeholder="Any destination"/></label>
          <button>Search</button><button type="button" className="secondary" onClick={()=>{setFrom('');setTo('');setTimeout(loadFlights,0);}}>Reset</button>
        </form>
      </section>

      <section className="panel">
        <div className="sectionHead"><h2>Available flights</h2><span className="count">{flights.length} found</span></div>
        <div className="flightGrid">
          {flights.map(f=><article className={`flightCard ${selected?.id===f.id?'chosen':''}`} key={f.id}>
            <div className="airline">{f.airline}<span>{f.flightNumber}</span></div>
            <div className="route"><div><strong>{f.origin}</strong><small>Origin</small></div><div className="routeLine">────────✈────────</div><div><strong>{f.destination}</strong><small>Destination</small></div></div>
            <div className="flightMeta"><span>🕒 {f.departureTime}</span><span>Seats: {f.availableSeats}</span></div>
            <div className="cardBottom"><strong className="price">₹{Number(f.price).toLocaleString('en-IN')}</strong><button onClick={()=>{setSelected(f);setMessage('');}}>Choose flight</button></div>
          </article>)}
          {!flights.length && <p className="muted">No matching flights. Try clearing your search.</p>}
        </div>
      </section>

      <section className="panel bookingPanel">
        <div><h2>Passenger details</h2><p className="muted">{selected?`Selected: ${selected.flightNumber} · ${selected.origin} to ${selected.destination}`:'Select a flight above to book.'}</p></div>
        <form className="bookingForm" onSubmit={bookFlight}>
          <label>Passenger name<input required maxLength="100" value={passengerName} onChange={e=>setPassengerName(e.target.value)} placeholder="Full name"/></label>
          <label>Email address<input required type="email" value={passengerEmail} onChange={e=>setPassengerEmail(e.target.value)} placeholder="name@example.com"/></label>
          <button disabled={!selected}>Confirm demo booking</button>
        </form>
      </section>

      <section className="panel">
        <div className="sectionHead"><h2>Your bookings</h2><button className="secondary" onClick={loadBookings}>Refresh</button></div>
        {bookings.length===0?<p className="muted">No bookings yet.</p>:<div className="bookingList">
          {bookings.slice().reverse().map(b=><div className="bookingRow" key={b.id}>
            <div><strong>Booking #{b.id} · {b.flightNumber}</strong><p>{b.passengerName} ({b.passengerEmail})</p><small>{b.origin} → {b.destination} · {b.departureTime}</small></div>
            <div className="bookingActions"><span className={`status ${b.status==='CANCELLED'?'cancelled':''}`}>{b.status}</span>{b.status!=='CANCELLED'&&<button className="danger" onClick={()=>cancelBooking(b.id)}>Cancel</button>}</div>
          </div>)}
        </div>}
      </section>
    </main>
    <footer>Student demo project · React frontend · Spring Boot microservices · H2 database</footer>
  </div>;
}
