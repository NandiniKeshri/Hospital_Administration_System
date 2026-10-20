# Hospital Administration System

## Deploy the frontend to Vercel

Create a Vercel project from this repository and set its **Root Directory** to `frontend`. Vercel will build the Vite app with `npm run build`; the output directory is `dist`. The included `frontend/vercel.json` supports direct links to client-side routes.

Set this project environment variable in Vercel:

| Variable | Value |
| --- | --- |
| `VITE_API_URL` | The public Render backend URL, for example `https://hospital-administration-api.onrender.com` |

Redeploy the frontend after changing environment variables.

## Deploy the backend to Render

Render can use the root `render.yaml` Blueprint to build the Spring Boot backend from `backend/Dockerfile`. The service exposes `/api/health` for health checks and uses Render's assigned `PORT` automatically.

Provide the prompted environment variables when creating the service:

| Variable | Value |
| --- | --- |
| `DB_HOST` | Hostname of a reachable MySQL server |
| `DB_PORT` | MySQL port, normally `3306` |
| `DB_NAME` | Database name |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `FRONTEND_URL` | The Vercel frontend origin; comma-separate additional allowed origins |

Render does not provision MySQL through this Blueprint, so configure a MySQL database separately and use credentials reachable from the Render service. The Blueprint generates a `JWT_SECRET` automatically.

The same frontend API setting can be placed in `frontend/.env.local` for local builds; copy `frontend/.env.example` and replace its example URL.
