using System.Drawing;
using System.Windows.Forms;

namespace tabletmouse.source {
	public partial class areaForm : Form {

		private bool dragging = false;
		private Point dragCursorPoint;
		private Point dragFormPoint;
		
		public areaForm() {
			InitializeComponent();
		}

		public void setBorderColor(Color CO) {
			lblTop.BackColor = CO;
			lblRig.BackColor = CO;
			lblLef.BackColor = CO;
			lblBut.BackColor = CO;
		}

		public int offsetResolution(bool coor) {
			int result = 0;
			if (coor) {
				result = this.Location.X ; // + label offset
			} else {
				result = this.Location.Y ; // + label offset
			} return result;
		}

		public void setSize(short X, short Y) {
			this.Size = new System.Drawing.Size(X, Y);

			Rectangle rect = new Rectangle(Point.Empty, lblBackground.Size);
			Region region = new Region(rect);
			rect.Inflate(-1 * (lblBackground.Width/2), -1 * (lblBackground.Height/2));
			region.Exclude(rect);
			lblBackground.Region = region;
		}

		//like press key mouse click
		private void areaForm_MouseDown(object sender, MouseEventArgs e) {
			dragging = true;
			dragCursorPoint = Cursor.Position;
			dragFormPoint = this.Location;
		}

		//move the mouse
		private void areaForm_MouseMove(object sender, MouseEventArgs e) {
			if (dragging) {
				Point dif = Point.Subtract(Cursor.Position, new Size(dragCursorPoint));
				this.Location = Point.Add(dragFormPoint, new Size(dif));
			}
		}

		//like release mouse click
		private void areaForm_MouseUp(object sender, MouseEventArgs e) {
			dragging = false;
        }
	}
}
